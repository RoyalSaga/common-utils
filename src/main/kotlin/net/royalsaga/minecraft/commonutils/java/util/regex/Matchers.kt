package net.royalsaga.minecraft.commonutils.java.util.regex

import java.util.regex.Matcher

operator fun Matcher.get(name: String): String {
    return this.group(name)
}

operator fun Matcher.get(group: Int): String {
    return this.group(group)
}