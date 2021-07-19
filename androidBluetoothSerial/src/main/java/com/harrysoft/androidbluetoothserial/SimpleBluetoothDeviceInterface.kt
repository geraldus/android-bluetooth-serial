package com.harrysoft.androidbluetoothserial

interface SimpleBluetoothDeviceInterface {
    /**
     * @return The BluetoothSerialDevice instance that the interface is wrapping.
     */
    val device: BluetoothSerialDevice

    fun sendMessage(message: String)

    fun sendRawMessage(bytes: ByteArray)

    /**
     * Set all of the listeners for the interfact
     *
     * @param messageReceivedListener Receive message callback
     * @param messageSentListener Send message callback (indicates that a message was successfully sent)
     * @param errorListener Error callback
     */
    fun setListeners(messageReceivedListener: OnMessageReceivedListener?,
                     messageSentListener: OnMessageSentListener?,
                     errorListener: OnErrorListener?)

    /**
     * Set the message received listener
     *
     * @param listener Receive message callback
     */
    fun setMessageReceivedListener(listener: OnMessageReceivedListener?)

    /**
     * Set the raw message received listener
     *
     * @param listener Receive message callback
     */
    fun setRawMessageReceivedListener(listener: OnRawMessageReceivedListener?)

    /**
     * Set the message sent listener
     *
     * @param listener Send message callback (indicates that a message was successfully sent)
     */
    fun setMessageSentListener(listener: OnMessageSentListener?)

    /**
     * Set the raw message sent listener
     *
     * @param listener Send raw message callback (indicates that a message was successfully sent)
     */
    fun setRawMessageSentListener(listener: OnRawMessageSentListener?)

    /**
     * Set the error listener
     *
     * @param listener Error callback
     */
    fun setErrorListener(listener: OnErrorListener?)

    interface OnMessageReceivedListener {
        fun onMessageReceived(message: String)
    }

    interface OnRawMessageReceivedListener {
        fun onRawMessageReceived(bytes: ByteArray)
    }

    interface OnMessageSentListener {
        fun onMessageSent(message: String)
    }

    interface OnRawMessageSentListener {
        fun onRawMessageSent(bytes: ByteArray)
    }

    interface OnErrorListener {
        fun onError(error: Throwable)
    }
}
