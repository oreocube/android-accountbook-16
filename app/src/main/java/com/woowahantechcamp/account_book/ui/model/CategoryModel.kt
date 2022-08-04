package com.woowahantechcamp.account_book.ui.model

data class CategoryModel(
    override val id: Int,
    override val title: String,
    val type: Type,
    val color: Int
) : Model