/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.captcha;

/**
 *
 * @author QuyetTC
 */
import java.awt.Font;

public class FontEx extends Font
{

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public FontEx(String name, int style, int size)
    {
        super(name, style, size);
    }

    public void setSize(int size)
    {
        this.size = size;
        pointSize = size;
    }
}
