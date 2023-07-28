package edu.alexey.javacore.homeworks.hw5;

import edu.alexey.javacore.homeworks.hw5.enums.CopyStatus;

public record FileBackupResult(String filePath, CopyStatus copyStatus, String details) {
}
