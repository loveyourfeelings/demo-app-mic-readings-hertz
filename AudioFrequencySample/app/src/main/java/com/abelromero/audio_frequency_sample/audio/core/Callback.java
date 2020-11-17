package com.abelromero.audio_frequency_sample.audio.core;

public interface Callback {
    void onBufferAvailable(long start, long before, long after, byte[] buffer);
}