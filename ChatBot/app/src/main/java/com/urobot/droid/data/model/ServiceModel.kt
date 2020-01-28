package com.urobot.droid.data.model


data class ServiceModel(
    val serviceId: String,
    val title: String,
    val description: String
)

enum class TypeServices(val type_id: Int){
    info(0),
    manager(1),
    onlineRecord(2),
    payment(3),
    back(4),
    leaveDialog(5)
}

