/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.utils;

import java.io.File;
import java.io.IOException;


public class Native2Ascii {

	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			return;
		}
		String arg = args[0];
		boolean reverse = false;
		if ("-reverse".equals(arg)) {
			reverse = true;
			if (args.length == 1) {
				return;
			}
			arg = args[1];
		}
		File file = new File( arg );
		String encoding = null;
		if ( (args.length > 1 && !reverse) || args.length > 2 ) {
			if (reverse) {
				encoding = args[2];
			} else {
				encoding = args[1];
			}
		}

	}


	public static String nativeToAscii( String input ) {
		if (input == null) {
			return null;
		}
		StringBuffer buffer = new StringBuffer( input.length() + 60 );
		for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c <= 0x7E) {
                buffer.append(c);
            }
            else {
            	buffer.append("\\u");
            	String hex = Integer.toHexString(c);
            	for (int j = hex.length(); j < 4; j++ ) {
            		buffer.append( '0' );
            	}
            	buffer.append( hex );
            }
        }
		return buffer.toString();
	}


	/**
	 * Translates the given String into ASCII code.
	 *
	 * @param input the input which contains native characters like umlauts etc
	 * @return the input in which native characters are replaced through ASCII code
	 */
	public static String asciiToNative( String input ) {
		if (input == null) {
			return null;
		}
		StringBuffer buffer = new StringBuffer( input.length() );
		boolean precedingBackslash = false;
		for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (precedingBackslash) {
            	switch (c) {
            	case 'f': c = '\f'; break;
            	case 'n': c = '\n'; break;
            	case 'r': c = '\r'; break;
            	case 't': c = '\t'; break;
            	case 'u':
            		String hex = input.substring( i + 1, i + 5 );
            		c = (char) Integer.parseInt(hex, 16 );
            		i += 4;
            	}
            	precedingBackslash = false;
            } else {
            	precedingBackslash = (c == '\\');
            }
            if (!precedingBackslash) {
                buffer.append(c);
            }
        }
		return buffer.toString();
	}


}

