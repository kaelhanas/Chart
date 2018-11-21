package  wave;


public abstract class WaveUtils {
	
	/**
	 * Utility method to deinterleave an interleaved buffer of amplitude data.
	 * @param buffer to deinterleave. Buffer's size has to be a multiple of channels.
	 * @param channels number of channels.
	 * @return the deinterleaved buffer, whose length is buffer.length / channels.
	 */
	public static int[] deinterleave(int[] buffer, int channels) {
		if(channels == 1)
			return buffer;
		
		if(buffer.length % channels != 0)
			throw new RuntimeException("Supplied buffer length is not a multiple of given channels' number");
		
		int[] deinterleaved = new int[buffer.length / channels];		
		for(int i=0; i<deinterleaved.length; i+=channels) {
			deinterleaved[i] = 0; 
			for(int j=0; j<channels; j++)
				deinterleaved[i] += buffer[i+j];
		}		
		return deinterleaved;
	}
	
	/**
	 * Utility method to transform a little endian byte buffer of PCM sound into int[]. 
	 * @param buffer
	 * @param encoding 8 or 16 bits.
	 * @return
	 */
	public static int[] getIntArray(byte[] buffer, int encoding) {
		int[] data = null;
		switch(encoding) {
		case 8:
			data = new int[buffer.length];
			for(int i=0; i<data.length; ++i)
				data[i] = (int) buffer[i];
			break;
		case 16:
			data = new int[buffer.length / 2];
			for(int i=0; i<data.length; ++i)
				data[i] = (int) (buffer[2*i] | (buffer[2*i + 1] << 8));
			break;
		}
		return data;
	}
}
