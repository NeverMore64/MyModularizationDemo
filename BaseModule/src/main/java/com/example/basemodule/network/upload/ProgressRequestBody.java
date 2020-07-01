package com.example.basemodule.network.upload;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by kongnan on 16/5/10.
 * OkHttp3上传进度
 */
public class ProgressRequestBody extends RequestBody {

    protected RequestBody mDelegate;
    protected ProgressListener mListener;
    protected CountingSink mCountingSink;

    public ProgressRequestBody(RequestBody delegate, ProgressListener listener) {
        mDelegate = delegate;
        mListener = listener;
    }

    @Override
    public MediaType contentType() {
        return mDelegate.contentType();
    }

    @Override
    public long contentLength() {
        try {
            return mDelegate.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        mCountingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(mCountingSink);
        mDelegate.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    protected final class CountingSink extends ForwardingSink {
        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWritten += byteCount;
            int progress = (int) (100F * bytesWritten / contentLength());
            mListener.onProgress(Math.min(progress, 99));
        }
    }

}