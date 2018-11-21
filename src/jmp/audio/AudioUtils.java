package jmp.audio;

import javax.sound.sampled.AudioFormat;

public class AudioUtils
{
	public static int[] getIntArray(AudioFormat format, byte[] audioBytes) {  
        
        return getIntArray(format, audioBytes, audioBytes.length);  
	}
	
	public static int[] getIntArray(AudioFormat format, byte[] audioBytes, int audioBytesLength)
	{
		int[] audioData = null;  
        if (format.getSampleSizeInBits() == 16) {  
             final int nlengthInSamples = audioBytesLength / 2;  
             audioData = new int[nlengthInSamples];  
             if (format.isBigEndian()) {  
                  for (int i = 0; i < nlengthInSamples; i++) {  
                       int MSB = audioBytes[2 * i];  
                       int LSB = audioBytes[2 * i + 1];  
                       audioData[i] = MSB << 8 | (255 & LSB);  
                  }  
             } else {  
                  for (int i = 0; i < nlengthInSamples; i++) {  
                       int LSB = audioBytes[2 * i];  
                       int MSB = audioBytes[2 * i + 1];  
                       audioData[i] = MSB << 8 | (255 & LSB);  
                  }  
             }  
        } else if (format.getSampleSizeInBits() == 8) {  
             final int nlengthInSamples = audioBytesLength;  
             audioData = new int[nlengthInSamples];  
             if (format.getEncoding().toString().startsWith("PCM_SIGN")) {  
                  // PCM_SIGNED  
                  for (int i = 0; i < nlengthInSamples; i++) {  
                       audioData[i] = audioBytes[i];  
                  }  
             } else {  
                  // PCM_UNSIGNED  
                  for (int i = 0; i < nlengthInSamples; i++) {  
                       audioData[i] = audioBytes[i] - 128;  
                  }  
             }  
        }  
        return audioData;  
	}
	
	public static byte[] getByteArray(AudioFormat format, int[] src)
	{
		final int srcLength = src.length;
		byte[] dst = null;

		if (format.getSampleSizeInBits() == 16)
		{
			dst = new byte[srcLength * 2];
			if (format.isBigEndian())
			{
				for (int i = 0, j = 0; i < srcLength; i++)
				{
					int x = src[i];
					dst[j++] = (byte) ((x >> 8) & 0xff);
					dst[j++] = (byte) ((x >> 0) & 0xff);
				}
			}
			else
			{
				for (int i = 0, j = 0; i < srcLength; i++)
				{
					int x = src[i];
					dst[j++] = (byte) ((x >> 0) & 0xff);
					dst[j++] = (byte) ((x >> 8) & 0xff);
				}
			}
		}
		else
		{
			dst = new byte[srcLength];
			if (format.getEncoding().toString().startsWith("PCM_SIGN")) for (int i = 0; i < srcLength; i++)
				dst[i] = (byte) src[i];
			else for (int i = 0; i < srcLength; i++)
				dst[i] = (byte) (src[i] + 128);

		}
		return dst;
	}
}
