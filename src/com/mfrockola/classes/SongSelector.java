package com.mfrockola.classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

class SongSelector
{
	JLabel labelSelector = new JLabel("- - - -");
	String values [] = {"-","-","-","-"};
	private String stringNumber = String.format("%s %s %s %s", values[0],values[1],values[2],values[3]);
	int counterValue = 0;
	boolean play = false;
	private int keyDelete;
	private int keyUpList;
	private int keyDownList;
	private int keyUpGender;
	private int keyDownGender;
	
	SongSelector(int keyDelete, int keyUpList, int keyDownList, int keyUpGender, int keyDownGender, int sizeSelector)
	{
		labelSelector.setFont(new Font("Consolas", Font.BOLD, sizeSelector));
		labelSelector.setOpaque(false);
		labelSelector.setForeground(Color.WHITE);
		labelSelector.setHorizontalAlignment(SwingUtilities.CENTER);
		this.keyDelete = keyDelete;
		this.keyUpList = keyUpList;
		this.keyDownList = keyDownList;
		this.keyUpGender = keyUpGender;
		this.keyDownGender = keyDownGender;
	}
	
	void resetValues()
	{
		values [0] = "-";
		values [1] = "-";
		values [2] = "-";
		values [3] = "-";
	}
	
	String keyEventHandler(KeyEvent e)
    {
        if (e.getKeyCode()== 48 || e.getKeyCode()==96)
        {
            updateStringNumber("0");
        }
        else if (e.getKeyCode()== 49 || e.getKeyCode()==97)
        {
            updateStringNumber("1");
        }
        else if (e.getKeyCode()== 50 || e.getKeyCode()==98)
        {
            updateStringNumber("2");
        }
        else if (e.getKeyCode()== 51 || e.getKeyCode()==99)
        {
            updateStringNumber("3");
        }
        else if (e.getKeyCode()== 52 || e.getKeyCode()==100)
        {
            updateStringNumber("4");
        }
        else if (e.getKeyCode()== 53 || e.getKeyCode()==101)
        {
            updateStringNumber("5");
        }
        else if (e.getKeyCode()== 54 || e.getKeyCode()==102)
        {
            updateStringNumber("6");
        }
        else if (e.getKeyCode()== 55 || e.getKeyCode()==103)
        {
            updateStringNumber("7");
        }
        else if (e.getKeyCode()== 56 || e.getKeyCode()==104)
        {
            updateStringNumber("8");
        }
        else if (e.getKeyCode()== 57 || e.getKeyCode()==105)
        {
            updateStringNumber("9");
        }
		
		if (e.getKeyCode()==keyDelete && counterValue > 0)
		{
			counterValue--;
			updateStringNumber("-");
		}
		else if (e.getKeyCode() == keyUpList || e.getKeyCode() == keyDownList || e.getKeyCode() == keyUpGender || e.getKeyCode() == keyDownGender) {
			counterValue = 0;
			resetValues();
			stringNumber = String.format("%s %s %s %s",values[0],values[1],values[2],values[3]);
		} else {
			if (counterValue < 3)
				++counterValue;
			else
			{
				play = true;
				counterValue = 0;
			}	
		}
		return stringNumber;
	}

    private void updateStringNumber(String key) {
        values[counterValue] = key;
        stringNumber = String.format("%s %s %s %s",values[0],values[1],values[2],values[3]);
    }
}