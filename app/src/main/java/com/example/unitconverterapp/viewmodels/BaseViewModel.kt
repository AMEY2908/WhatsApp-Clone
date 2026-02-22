package com.example.unitconverterapp.viewmodels


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.unitconverterapp.homescreen.ChatList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import java.io.ByteArrayInputStream
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(): ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // -----------------------------
    // SEARCH USER
    // -----------------------------
    fun searchUserByPhoneNumber(
        phoneNumber: String,
        callback: (ChatList?) -> Unit
    ) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.e("BaseViewModel", "User not authenticated")
            callback(null)
            return
        }

        database.getReference("users")
            .orderByChild("phoneNumber")
            .equalTo(phoneNumber)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.children.firstOrNull()
                        ?.getValue(ChatList::class.java)
                    callback(user)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BaseViewModel", error.message)
                    callback(null)
                }
            })
    }

    // -----------------------------
    // CHAT LIST FLOW
    // -----------------------------
    private val _chatList = MutableStateFlow<List<ChatList>>(emptyList())
    val chatList = _chatList.asStateFlow()

    private var chatListener: ValueEventListener? = null
    private var chatRef: DatabaseReference? = null

    init {
        loadChatData()
    }

    private fun loadChatData() {
        val currentUserId = auth.currentUser?.uid ?: return

        chatRef = database.getReference("chats")

        chatListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ChatList>()
                for (child in snapshot.children) {
                    val chat = child.getValue(ChatList::class.java)
                    if (chat != null && chat.userId == currentUserId) {
                        list.add(chat)
                    }
                }
                _chatList.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("BaseViewModel", error.message)
            }
        }

        chatRef?.addValueEventListener(chatListener!!)
    }

    override fun onCleared() {
        super.onCleared()
        chatListener?.let {
            chatRef?.removeEventListener(it)
        }
    }

    // -----------------------------
    // ADD CHAT
    // -----------------------------
    fun addChat(newChat: ChatList) {
        val currentUserId = auth.currentUser?.uid ?: return

        val newChatRef = database.getReference("chats").push()
        val chatWithUser = newChat.copy(userId = currentUserId)

        newChatRef.setValue(chatWithUser)
            .addOnSuccessListener {
                Log.d("BaseViewModel", "Chat added")
            }
            .addOnFailureListener {
                Log.e("BaseViewModel", it.message ?: "Error")
            }
    }
    fun getChatForUser(userId: String, callback: (List<ChatList>) -> Unit) {
        database.getReference("chats")
            .orderByChild("userId").equalTo(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.children.mapNotNull { it.getValue(ChatList::class.java) }
                    callback(list)
                }
                override fun onCancelled(error: DatabaseError) {
                    callback(emptyList())
                }
            })
    }

    // -----------------------------
    // SEND MESSAGE
    // -----------------------------
    fun sendMessage(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        messageText: String
    ) {
        val messageId = database.reference.push().key ?: return

        val message = Message(
            senderPhoneNumber = senderPhoneNumber,
            message = messageText,
            timeStamp = System.currentTimeMillis()
        )

        database.reference.child("messages")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)
            .child(messageId)
            .setValue(message)

        database.reference.child("messages")
            .child(receiverPhoneNumber)
            .child(senderPhoneNumber)
            .child(messageId)
            .setValue(message)
    }

    // -----------------------------
    // GET MESSAGES (REALTIME)
    // -----------------------------
    fun getMessage(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        onMessage: (Message) -> Unit
    ) {
        database.reference.child("messages")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)
            .addChildEventListener(object : ChildEventListener {

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    snapshot.getValue(Message::class.java)?.let {
                        onMessage(it)
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onChildRemoved(snapshot: DataSnapshot) {}
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    // -----------------------------
    // FETCH LAST MESSAGE (FIXED PATH + TYPE)
    // -----------------------------
    fun fetchLastMessageForChat(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        onLastMessageFetched: (String, String) -> Unit
    ) {
        database.reference.child("messages")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)
            .orderByChild("timeStamp")
            .limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val child = snapshot.children.first()
                        val message = child.child("message").value as? String
                        val timeStamp = child.child("timeStamp").value as? Long

                        onLastMessageFetched(
                            message ?: "No Message",
                            timeStamp?.toString() ?: "--:--"
                        )
                    } else {
                        onLastMessageFetched("No Message", "--:--")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onLastMessageFetched("No Message", "--:--")
                }
            })
    }

    // -----------------------------
    // LOAD CHAT LIST (NO RACE CONDITION)
    // -----------------------------
    fun loadChatList(
        currentUserPhoneNumber: String,
        onChatListLoaded: (List<ChatList>) -> Unit
    ) {
        val chatList = mutableListOf<ChatList>()

        database.reference.child("chats")
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!snapshot.exists()) {
                        onChatListLoaded(emptyList())
                        return
                    }

                    val totalChildren = snapshot.childrenCount.toInt()
                    var loadedCount = 0

                    snapshot.children.forEach { child ->

                        val phoneNumber = child.key ?: return@forEach
                        val name = child.child("name").value as? String ?: "Unknown"
                        val image = child.child("profileImage").value as? String

                        fetchLastMessageForChat(
                            currentUserPhoneNumber,
                            phoneNumber
                        ) { lastMessage, time ->

                            chatList.add(
                                ChatList(
                                    name = name,
                                    profileImage = image,
                                    message = lastMessage,
                                    time = time
                                )
                            )

                            loadedCount++
                            if (loadedCount == totalChildren) {
                                onChatListLoaded(chatList)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onChatListLoaded(emptyList())
                }
            })
    }

    // -----------------------------
    // BASE64 TO BITMAP
    // -----------------------------
    private fun decodeBase64toBitmap(base64Image: String): Bitmap? {
        return try {
            val decodeByte = Base64.decode(base64Image, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.size)
        } catch (e: IOException) {
            null
        }
    }

    fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodeByte = Base64.decode(base64String, Base64.DEFAULT)
            val inputStream: InputStream = ByteArrayInputStream(decodeByte)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            null
        }
    }
}