package net.lexicodes.API.multiversion

import org.bukkit.Bukkit

enum class Version {
    UNKNOWN, v1_7, v1_8, v1_9, v1_10, v1_11, v1_12, v1_13, v1_14, v1_14_3, v1_15, v1_16, v1_17, v1_18, v1_19;

    fun isBiggerThan(version: Version): Boolean {
        val current = index
        val param = version.index
        return current > param
    }

    val index: Int
        get() {
            var index = 0
            for (v in values()) {
                if (this == v) return index else index++
            }
            return -1
        }

    companion object {
        val version: Version
            get() = if (Bukkit.getVersion()
                    .contains("1.7")
            ) v1_7 else if (Bukkit.getVersion()
                    .contains("1.8")
            ) v1_8 else if (Bukkit.getVersion()
                    .contains("1.9")
            ) v1_9 else if (Bukkit.getVersion()
                    .contains("1.10")
            ) v1_10 else if (Bukkit.getVersion()
                    .contains("1.11")
            ) v1_11 else if (Bukkit.getVersion()
                    .contains("1.12")
            ) v1_12 else if (Bukkit.getVersion()
                    .contains("1.13")
            ) v1_13 else if (Bukkit.getVersion().contains("1.14")) {
                if (Bukkit.getVersion() == "1.14" || Bukkit.getVersion().contains("1.14 ")
                    || Bukkit.getVersion().contains("1.14.1") || Bukkit.getVersion().contains("1.14.2")
                    || Bukkit.getVersion().contains("1.14.3")
                ) {
                    v1_14
                } else {
                    v1_14_3
                }
            } else if (Bukkit.getVersion()
                    .contains("1.15")
            ) v1_15 else if (Bukkit.getVersion()
                    .contains("1.16")
            ) v1_16 else if (Bukkit.getVersion()
                    .contains("1.17")
            ) v1_17 else if (Bukkit.getVersion()
                    .contains("1.18")
            ) v1_18 else if (Bukkit.getVersion()
                    .contains("1.19")
            ) v1_19 else UNKNOWN
        val bukkitVersion: String
            get() = Bukkit.getServer().javaClass.getPackage().name.substring(23)
    }
}