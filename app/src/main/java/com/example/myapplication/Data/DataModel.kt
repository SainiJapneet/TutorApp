package com.example.myapplication.Data

object DataModel{
    data class TeacherModel(
        val uid: String,
        val name: String,
        val email: String,
        val groups: String,
        val tests: String
    )

    data class UserCredentials(
        val uid: String,
        val name: String,
        val email: String,
        val countryCode: String,
        val phone: String,
        var image: String,
        val isVerified: Boolean
    )

    data class Group(
        val groupId: String,
        val groupName: String,
        val description: String,
        val subjectId: String,
        val tutorId: String,
        val coverImage: String,
        val displayImage: String,
        val students: List<String>
    )

    data class  Subjects(
        val subjectId: String,
        val name: String
    )

    data class Students(
        val studentId: String = "",
        val name: String = "",
        val email: String = "",
        val countryCode: String = "",
        val phone: String = "",
        var image: String = ""
    )

    data class Messages(
        val messageId: String,
        val groupId: String,
        val senderId: String,
        val content: String,
        val timestamp: String
    )

    data class Test(
        val testId: String,
        val creatorId: String,
        val assignedTo: List<String>,
        var questions: List<Question>
    )

    data class Question(
        var questionId: String,
        var text: String,
        var options: List<String>,
        var correctOption: String,
        var marks: Int
    )
}