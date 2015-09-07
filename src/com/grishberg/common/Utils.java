package com.grishberg.common;

import java.util.*;
//
public class Utils
{
	public static boolean camelVarName = true;
	public static String generateSqlType(String s){
		s = s.toLowerCase();
		switch(s){
			case "string":
				return "TEXT";
			case "int":
				return "INTEGER";
			case "long":
				return "INTEGER";
			case "date":
				return "INTEGER";
			case "boolean":
				return "INTEGER";
		}
		return null;
	}
	
	public static String generateCursorGetter(String s){
		s = s.toLowerCase();
		switch(s){
			case "string":
				return "getString";
			case "int":
				return "getInt";
			case "long":
				return "getLong";
			case "date":
				return "getLong";
			case "boolean":
				return "getInt";
		}
		return null;
	}
	
	public static String generateClassName(String s){
		System.out.println(s);
		StringBuilder result = 
			new StringBuilder( );
		String[] words = s.split("_");
		for(String word: words){
			if(word.length() == 0) continue;
			result.append(Character.toUpperCase(word.charAt(0)) );
			result.append(word.substring(1).toLowerCase());
		}
		return result.toString();
	}
	
	public static String generateVarName(String oldName){
		StringBuilder newName = new StringBuilder();
		if(camelVarName){
			return generateVarNameCamel(oldName);
		}
		String [] words = oldName.toLowerCase().split("_");
		boolean firstSymbol = true;
		for (String word :words){
			if( firstSymbol){
				newName.append( Character.toLowerCase( word.charAt(0)));
				firstSymbol = false;
			} else {
				newName.append( Character.toUpperCase( word.charAt(0)));
			}
			if( word.length() > 1){
				newName.append(word.substring(1));
			}
		}
		return newName.toString();
	}

	public static String generateVarNameCamel(String name){
		List<Character> out = new ArrayList();
		boolean isUpper = true;

		for (int i = 0; i < name.length(); i++){
			Character c = name.charAt(i);
			if (Character.isUpperCase(c)){
				if (! isUpper){
					//camel
					out.add('_');
				}
				out.add(c);
				isUpper = true;
			} else {
				if( isUpper) {
					if(out.size()>1){
						if(out.get( out.size()-2) != '_'){
							out.add(out.size()-1,'_');
						}
					}
				}
				isUpper = false;
				out.add(c);
			}
		}
		StringBuilder result = new StringBuilder();
		Character lastChar = null;
		boolean isCamel = false;
		for(Character c: out){
			if(c != '_') {
				if (isCamel){
					isCamel = false;
					c = Character.toUpperCase(c);
				} else {
					c = Character.toLowerCase(c);
				}
				result.append(c);
			} else {
				isCamel = true;
			}
		}
		return result.toString();
	}


	public static String generateConstName(String name){
		List<Character> out = new ArrayList();
		boolean isUpper = true;
		
		for (int i = 0; i < name.length(); i++){
			Character c = name.charAt(i);
			if (Character.isUpperCase(c)){
				if (! isUpper){
					//camel
					out.add('_');
				}
				out.add(c);
				isUpper = true;
			} else {
				if( isUpper) {
					if(out.size()>1){
						if(out.get( out.size()-2) != '_'){
							out.add(out.size()-1,'_');
						}
					}
				}
				isUpper = false;
				out.add(c);
			}
		}
		StringBuilder result = new StringBuilder();
		Character lastChar = null;
		for(Character c: out){
			result.append(c);
		}
		return result.toString().toUpperCase();
	}
	
	
}
