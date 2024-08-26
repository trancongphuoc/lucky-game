/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.bo;
import java.io.Serializable;

@SuppressWarnings("serial")
public class LukyGameInvitedFriendHis implements Serializable {
	private int id;
	private String isdn;
	private String invitedIsdn;
	private String insertTime;
	private String result;
	
	

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}



	/**
	 * @return the isdn
	 */
	public String getIsdn() {
		return isdn;
	}



	/**
	 * @param isdn the isdn to set
	 */
	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

        public String getInvitedIsdn() {
            return invitedIsdn;
        }

        public void setInvitedIsdn(String invitedIsdn) {
            this.invitedIsdn = invitedIsdn;
        }



	/**
	 * @return the insertTime
	 */
	public String getInsertTime() {
		return insertTime;
	}



	/**
	 * @param insertTime the insertTime to set
	 */
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}



	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}



	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MiniGame2018ShareFriendHis [id=" + id + ", isdn=" + isdn + ", invitedIsdn=" + invitedIsdn
				+ ", insertTime=" + insertTime + ", result=" + result + "]";
	}

}
