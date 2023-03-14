package com.yazaki_groupcom.app.enum

/**
 * 切断工程の設備
 */
enum class ShareKey(val key: String) {
    CurrentUserName("currentUserName"),                 //現在使用中のユーザー名
    LastSelectedProcessName("lastSelectedProcess"),     //最後に選択したプロセス
}