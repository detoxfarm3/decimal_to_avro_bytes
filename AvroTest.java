
import java.util.*;
import java.lang.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.math.BigDecimal;   

import java.math.RoundingMode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.nio.charset.StandardCharsets;

/* Name of the class has to be "Main" only if the class is public. */
class AvroTest
{
	public static void main (String[] args) throws java.lang.Exception {
		String[] values = args[0].toString().split(",");
		for (String val : values) {
			BigDecimal bdValue = new BigDecimal(val);  
			ByteBuffer value = toBytes(bdValue);
			StringBuilder builder =new StringBuilder();
			CharSequence str = StandardCharsets.ISO_8859_1.decode(value);
			StringBuilder second = new StringBuilder();
			second.append(val + " Binary -> '\\u0000");
			for (int i = 0; i < str.length(); i++) {
				char ch = str.charAt(i);
				second.append(ch);
				String hex = Integer.toHexString(ch);
				builder.append("\\u");
				for (int j = 0; j < 4 - hex.length(); j++)
					builder.append('0');
				builder.append(hex.toUpperCase());
			}
			second.append("'");
			System.out.println(val + " Hex -> " + builder.toString());
			System.out.println(second.toString());
		}
		
		// String s = "\u0000♫►";
		// ByteBuffer buf = ByteBuffer.wrap(s.getBytes(StandardCharsets.ISO_8859_1));
		// System.out.println(fromBytes(buf));
	}
	// @Override
    public static BigDecimal fromBytes(ByteBuffer value) {
      int scale = 2;
      // always copy the bytes out because BigInteger has no offset/length ctor
      byte[] bytes = new byte[value.remaining()];
      value.duplicate().get(bytes);
      return new BigDecimal(new BigInteger(bytes), scale);
    }
    // @Override
    public static ByteBuffer toBytes(BigDecimal value) {
	  value = value.setScale(2, RoundingMode.UNNECESSARY);
      return ByteBuffer.wrap(value.unscaledValue().toByteArray());
    }
}