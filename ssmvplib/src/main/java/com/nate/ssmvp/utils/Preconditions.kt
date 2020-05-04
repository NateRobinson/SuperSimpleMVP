package com.nate.ssmvp.utils

/**
 * Created by Nate on 2020/5/4
 */
object Preconditions {
  fun checkState(expression: Boolean, errorMessageTemplate: String?, vararg errorMessageArgs: Any?) {
    check(expression) { format(errorMessageTemplate, *errorMessageArgs) }
  }

  fun <T> checkNotNull(reference: T?): T {
    return if (reference == null) {
      throw NullPointerException()
    } else {
      reference
    }
  }

  fun <T> checkNotNull(reference: T?, errorMessage: Any?): T {
    return reference ?: throw NullPointerException(errorMessage.toString())
  }

  fun <T> checkNotNull(reference: T?, errorMessageTemplate: String?, vararg errorMessageArgs: Any?): T {
    return reference ?: throw NullPointerException(format(errorMessageTemplate, *errorMessageArgs))
  }

  private fun format(templateOrigin: String?, vararg args: Any?): String {
    var template = templateOrigin
    template = template.toString()
    val builder = StringBuilder(template.length + 16 * args.size)
    var templateStart = 0
    var placeholderStart: Int
    var i: Int = 0
    while (i < args.size) {
      placeholderStart = template.indexOf("%s", templateStart)
      if (placeholderStart == -1) {
        break
      }
      builder.append(template.substring(templateStart, placeholderStart))
      builder.append(args[i++])
      templateStart = placeholderStart + 2
    }
    builder.append(template.substring(templateStart))
    if (i < args.size) {
      builder.append(" [")
      builder.append(args[i++])
      while (i < args.size) {
        builder.append(", ")
        builder.append(args[i++])
      }
      builder.append(']')
    }
    return builder.toString()
  }
}