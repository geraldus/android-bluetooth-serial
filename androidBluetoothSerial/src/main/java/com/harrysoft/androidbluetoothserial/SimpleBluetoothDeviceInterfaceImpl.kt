package com.harrysoft.androidbluetoothserial

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Implementation of SimpleBluetoothDeviceInterface, package-private
 */
internal class SimpleBluetoothDeviceInterfaceImpl(override val device: BluetoothSerialDeviceImpl) : SimpleBluetoothDeviceInterface {
    private val compositeDisposable = CompositeDisposable()

    private var messageReceivedListener: SimpleBluetoothDeviceInterface.OnMessageReceivedListener? = null
    private var messageSentListener: SimpleBluetoothDeviceInterface.OnMessageSentListener? = null
    private var rawMessageReceivedListener: SimpleBluetoothDeviceInterface.OnRawMessageReceivedListener? = null
    private var rawMessageSentListener: SimpleBluetoothDeviceInterface.OnRawMessageSentListener? = null
    private var errorListener: SimpleBluetoothDeviceInterface.OnErrorListener? = null

    init {
        compositeDisposable.add(device.openMessageStream()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ messageReceivedListener?.onMessageReceived(it) }, { errorListener?.onError(it) }))
        compositeDisposable.add(device.openRawMessageStream()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ rawMessageReceivedListener?.onRawMessageReceived(it) }, { errorListener?.onError(it) }))
    }

    override fun sendMessage(message: String) {
        device.checkNotClosed()
        compositeDisposable.add(device.send(message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ messageSentListener?.onMessageSent(message) }, { errorListener?.onError(it) }))
    }

    override fun sendRawMessage(bytes: ByteArray) {
        device.checkNotClosed()
        compositeDisposable.add(device.sendRaw(bytes)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ rawMessageSentListener?.onRawMessageSent(bytes) }, { errorListener?.onError(it) }))
    }

    override fun setListeners(messageReceivedListener: SimpleBluetoothDeviceInterface.OnMessageReceivedListener?,
                              messageSentListener: SimpleBluetoothDeviceInterface.OnMessageSentListener?,
                              errorListener: SimpleBluetoothDeviceInterface.OnErrorListener?) {
        this.messageReceivedListener = messageReceivedListener
        this.messageSentListener = messageSentListener
        this.errorListener = errorListener
    }

    override fun setMessageReceivedListener(listener: SimpleBluetoothDeviceInterface.OnMessageReceivedListener?) {
        messageReceivedListener = listener
    }

    override fun setRawMessageReceivedListener(listener: SimpleBluetoothDeviceInterface.OnRawMessageReceivedListener?) {
        rawMessageReceivedListener = listener
    }

    override fun setMessageSentListener(listener: SimpleBluetoothDeviceInterface.OnMessageSentListener?) {
        messageSentListener = listener
    }

    override fun setRawMessageSentListener(listener: SimpleBluetoothDeviceInterface.OnRawMessageSentListener?) {
        rawMessageSentListener = listener
    }

    override fun setErrorListener(listener: SimpleBluetoothDeviceInterface.OnErrorListener?) {
        errorListener = listener
    }

    fun close() {
        compositeDisposable.dispose()
    }
}
