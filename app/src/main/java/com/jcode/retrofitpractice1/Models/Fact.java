package com.jcode.retrofitpractice1.Models;

/**
 * Created by otimj on 9/28/2019.
 */

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fact implements Serializable, Parcelable
{

	@SerializedName("used")
	@Expose
	private boolean used;
	@SerializedName("source")
	@Expose
	private String source;
	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("deleted")
	@Expose
	private boolean deleted;
	@SerializedName("_id")
	@Expose
	private String id;
	@SerializedName("__v")
	@Expose
	private int v;
	@SerializedName("text")
	@Expose
	private String text;
	@SerializedName("updatedAt")
	@Expose
	private String updatedAt;
	@SerializedName("createdAt")
	@Expose
	private String createdAt;
	@SerializedName("user")
	@Expose
	private String user;
	public final static Parcelable.Creator<Fact> CREATOR = new Creator<Fact>() {


		@SuppressWarnings({
				"unchecked"
		})
		public Fact createFromParcel(Parcel in) {
			return new Fact(in);
		}

		public Fact[] newArray(int size) {
			return (new Fact[size]);
		}

	}
			;
	private final static long serialVersionUID = 4144573155197315664L;

	protected Fact(Parcel in) {
		this.used = ((boolean) in.readValue((boolean.class.getClassLoader())));
		this.source = ((String) in.readValue((String.class.getClassLoader())));
		this.type = ((String) in.readValue((String.class.getClassLoader())));
		this.deleted = ((boolean) in.readValue((boolean.class.getClassLoader())));
		this.id = ((String) in.readValue((String.class.getClassLoader())));
		this.v = ((int) in.readValue((int.class.getClassLoader())));
		this.text = ((String) in.readValue((String.class.getClassLoader())));
		this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
		this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
		this.user = ((String) in.readValue((String.class.getClassLoader())));
	}

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Fact() {
	}

	/**
	 *
	 * @param updatedAt
	 * @param id
	 * @param v
	 * @param text
	 * @param source
	 * @param createdAt
	 * @param type
	 * @param used
	 * @param user
	 * @param deleted
	 */
	public Fact(boolean used, String source, String type, boolean deleted, String id, int v, String text, String updatedAt, String createdAt, String user) {
		super();
		this.used = used;
		this.source = source;
		this.type = type;
		this.deleted = deleted;
		this.id = id;
		this.v = v;
		this.text = text;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
		this.user = user;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}



	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(used);
		dest.writeValue(source);
		dest.writeValue(type);
		dest.writeValue(deleted);
		dest.writeValue(id);
		dest.writeValue(v);
		dest.writeValue(text);
		dest.writeValue(updatedAt);
		dest.writeValue(createdAt);
		dest.writeValue(user);
	}

	public int describeContents() {
		return  0;
	}

}

