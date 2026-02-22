package com.example.unitconverterapp.homescreen


data class ChatList(
    val name: String?=null,
    val phoneNumber:String?=null,
    val time: String?=null,
    val image: Int?=null,
    val message: String?=null,
    val userId:String?=null,
    val profileImage:String?=null
){
    constructor():this(null,null,null,null,null,null,null)
}