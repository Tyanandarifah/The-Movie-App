package com.example.common.ui

data class DialogData(
    val title: String,
    val message: String,
    val positiveButton: Pair<String,()->Unit>?,
    val negativeButton: Pair<String,()->Unit>?
)