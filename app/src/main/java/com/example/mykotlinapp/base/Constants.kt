package com.example.mykotlinapp.base

import com.example.mykotlinapp.models.Student

typealias StudentsCompletion = (List<Student>) -> Unit
typealias StudentCompletion = (Student?) -> Unit  // Allow nullable for cases where student not found
typealias Completion = () -> Unit