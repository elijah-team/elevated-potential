/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017 Cloudogu GmbH
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


package com.cloudogu.blog;

import com.github.mustachejava.util.DecoratedCollection;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class Scope {

	private static final String COMMENT = "generated by ToJsonProcessor";

	private          ZonedDateTime date;
	private          String        packageName;
	private          String        sourceClassName;
	private @NotNull List<Field>   fields = new ArrayList<>();

	Scope(String packageName, String sourceClassName) {
		this.date            = ZonedDateTime.now();
		this.packageName     = packageName;
		this.sourceClassName = sourceClassName;
	}

	public @NotNull String getComment() {
		return COMMENT;
	}

	public @NotNull String getDate() {
		return date.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}

	public String getPackageName() {
		return packageName;
	}

	public String getSourceClassName() {
		return sourceClassName;
	}

	public @NotNull String getSourceClassNameWithPackage() {
		return packageName + "." + sourceClassName;
	}

	public @NotNull String getTargetClassNameWithPackage() {
		return packageName + "." + getTargetClassName();
	}

	public @NotNull String getTargetClassName() {
		return sourceClassName + "JsonWriter";
	}

	public @NotNull DecoratedCollection<Field> getFields() {
		return new DecoratedCollection(fields);
	}

	public void addGetter(@NotNull String getter) {
		String fieldName = getter.substring(3);
		char   firstChar = fieldName.charAt(0);
		fieldName = Character.toLowerCase(firstChar) + fieldName.substring(1);
		fields.add(new Field(fieldName, getter));
	}

	public static class Field {

		private String name;
		private String getter;

		private Field(String name, String getter) {
			this.name   = name;
			this.getter = getter;
		}

		public String getName() {
			return name;
		}

		public String getGetter() {
			return getter;
		}
	}

}
