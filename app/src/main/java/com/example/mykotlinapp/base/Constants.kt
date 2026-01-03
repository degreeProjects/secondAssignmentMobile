package com.example.mykotlinapp.base

import com.example.mykotlinapp.models.Student

typealias StudentsCompletion = (List<Student>) -> Unit
typealias StudentCompletion = (Student) -> Unit
typealias Completion = () -> Unit