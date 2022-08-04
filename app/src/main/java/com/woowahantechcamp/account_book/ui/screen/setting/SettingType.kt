package com.woowahantechcamp.account_book.ui.screen.setting

enum class SettingType(
    val plainTitle: String,
    val addTitle: String,
    val editTitle: String
) {
    PAYMENT("결제수단", "결제수단 추가하기", "결제수단 수정하기"),
    INCOME("수입", "수입 카테고리 추가", "수입 카테고리 수정"),
    EXPENSE("지출", "지출 카테고리 추가", "지출 카테고리 수정")
}