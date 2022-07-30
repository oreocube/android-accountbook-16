package com.woowahantechcamp.account_book.data.repository

import com.woowahantechcamp.account_book.ui.model.setting.HistoryModel
import com.woowahantechcamp.account_book.ui.model.setting.Type

val list = listOf(
    HistoryModel(
        date = "7월 15일 금",
        type = Type.EXPENSES,
        content = "스트리밍 서비스 정기 결제",
        amount = 10900,
        payment = "현대카드",
        category = "문화/여가",
        color = "#D092E2"
    ),
    HistoryModel(
        date = "7월 15일 금",
        type = Type.EXPENSES,
        content = "후불 교통비 결제",
        amount = 45340,
        payment = "현대카드",
        category = "교통",
        color = "#94D3CC"
    ),
    HistoryModel(
        date = "7월 13일 수",
        type = Type.EXPENSES,
        content = "온라인 세미나 신청",
        amount = 10000,
        payment = "현대카드",
        category = "미분류",
        color = "#817DCE"
    ),
    HistoryModel(
        date = "7월 10일 일",
        type = Type.EXPENSES,
        content = "두유 4개",
        amount = 19140,
        payment = "현대카드",
        category = "식비",
        color = "#4A6CC3"
    ),
    HistoryModel(
        date = "7월 10일 일",
        type = Type.EXPENSES,
        content = "7월 월세",
        amount = 500000,
        payment = "카카오뱅크 체크카드",
        category = "생활",
        color = "#D092E2"
    ),
    HistoryModel(
        date = "7월 9일 토",
        type = Type.EXPENSES,
        content = "잔치국수와 김밥",
        amount = 9500,
        payment = "현대카드",
        category = "식비",
        color = "#4A6CC3"
    ),
    HistoryModel(
        date = "7월 9일 토",
        type = Type.INCOME,
        content = "7월 월급",
        amount = 1822480,
        payment = "카카오뱅크 체크카드",
        category = "월급",
        color = "#9BD182"
    ),
    HistoryModel(
        date = "7월 8일 금",
        type = Type.EXPENSES,
        content = "스트리밍 서비스 정기 결제",
        amount = 10900,
        payment = "현대카드",
        category = "문화/여가",
        color = "#D092E2"
    ),
    HistoryModel(
        date = "7월 8일 금",
        type = Type.EXPENSES,
        content = "스트리밍 서비스 정기 결제",
        amount = 10900,
        payment = "현대카드",
        category = "문화/여가",
        color = "#D092E2"
    ),
)

class HistoryRepository() {

    fun getAll() = list.groupBy { it.date }

}
