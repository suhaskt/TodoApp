package com.suhas.todoapplication.helperFunctions

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


fun formatTimestamp(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")
        .withZone(ZoneId.systemDefault())
    return formatter.format(Instant.ofEpochMilli(timestamp))
}

