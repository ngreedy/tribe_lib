package com.gs.buluo.common.widget.util

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader

/**
 *  @Author:        hjn
 * @CreateDate:     2019/6/17 15:57
 * @Description:
 *
 */
object ShellUtils {

    private val LINE_SEP = System.getProperty("line.separator")

    /**
     * Execute the command.
     *
     * @param command  The command.
     * @param isRooted True to use root, false otherwise.
     * @return the single [CommandResult] instance
     */
    fun execCmd(command: String, isRooted: Boolean): CommandResult {
        return execCmd(arrayOf(command), isRooted, true)
    }

    /**
     * Execute the command.
     *
     * @param commands        The commands.
     * @param isRooted        True to use root, false otherwise.
     * @param isNeedResultMsg True to return the message of result, false otherwise.
     * @return the single [CommandResult] instance
     */
    fun execCmd(
        commands: Array<String>?,
        isRooted: Boolean,
        isNeedResultMsg: Boolean
    ): CommandResult {
        var result = -1
        if (commands == null || commands.size == 0) {
            return CommandResult(result, null, null)
        }
        var process: Process? = null
        var successResult: BufferedReader? = null
        var errorResult: BufferedReader? = null
        var successMsg: StringBuilder? = null
        var errorMsg: StringBuilder? = null
        var os: DataOutputStream? = null
        try {
            process = Runtime.getRuntime().exec(if (isRooted) "su" else "sh")
            os = DataOutputStream(process!!.outputStream)
            for (command in commands) {
                if (command == null) continue
                os.write(command.toByteArray())
                os.writeBytes(LINE_SEP)
                os.flush()
            }
            os.writeBytes("exit$LINE_SEP")
            os.flush()
            result = process.waitFor()
            if (isNeedResultMsg) {
                successMsg = StringBuilder()
                errorMsg = StringBuilder()
                successResult = BufferedReader(
                    InputStreamReader(
                        process.inputStream,
                        "UTF-8"
                    )
                )
                errorResult = BufferedReader(
                    InputStreamReader(
                        process.errorStream,
                        "UTF-8"
                    )
                )

                successResult.lineSequence().forEach {
                    it?.let {
                        successMsg?.append(LINE_SEP).append(it)

                    }
                }
                errorResult.lineSequence().forEach {
                    it?.let {
                        errorMsg?.append(LINE_SEP).append(it)

                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                os?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                successResult?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                errorResult?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            process?.destroy()
        }
        return CommandResult(
            result,
            successMsg?.toString(),
            errorMsg?.toString()
        )
    }


    /**
     * The result of command.
     */
    class CommandResult(var result: Int, var successMsg: String?, var errorMsg: String?)
}