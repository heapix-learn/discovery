package com.heapixLearn.discovery.entity

class Post(
    var access: Int,
    var userId: Int,
    var description: String?,
    var title: String?,
    var imgRefList: List<String>?,
    var videoList: List<VideoItem>?,
    var lat: Double,
    var lng: Double,
    var time: Long
) {
    var id: Int = 0
}
