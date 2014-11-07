package com.jspsmart.upload;

/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import javax.servlet.ServletException;
/*     */ 
/*     */ public class File
/*     */ {
/*     */   private SmartUpload m_parent;
/*  29 */   private int m_startData = 0;
/*  30 */   private int m_endData = 0;
/*  31 */   private int m_size = 0;
/*  32 */   private String m_fieldname = new String();
/*  33 */   private String m_filename = new String();
/*  34 */   private String m_fileExt = new String();
/*  35 */   private String m_filePathName = new String();
/*  36 */   private String m_contentType = new String();
/*  37 */   private String m_contentDisp = new String();
/*  38 */   private String m_typeMime = new String();
/*  39 */   private String m_subTypeMime = new String();
/*  40 */   private String m_contentString = new String();
/*  41 */   private boolean m_isMissing = true;
/*     */   public static final int SAVEAS_AUTO = 0;
/*     */   public static final int SAVEAS_VIRTUAL = 1;
/*     */   public static final int SAVEAS_PHYSICAL = 2;
/*     */ 
/*     */   public void saveAs(String paramString)
/*     */     throws IOException, SmartUploadException
/*     */   {
/*  65 */     saveAs(paramString, 0);
/*     */   }
/*     */ 
/*     */   public void saveAs(String paramString, int paramInt)
/*     */     throws IOException, SmartUploadException
/*     */   {
/*  90 */     String str = new String();
/*  91 */     str = this.m_parent.getPhysicalPath(paramString, paramInt);
/*     */ 
/*  94 */     if (str == null) {
/*  95 */       throw new IllegalArgumentException("There is no specified destination file (1140).");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 100 */       java.io.File localFile = new java.io.File(str);
/*     */ 
/* 103 */       FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
/* 104 */       localFileOutputStream.write(this.m_parent.m_binArray, this.m_startData, this.m_size);
/* 105 */       localFileOutputStream.close();
/*     */     }
/*     */     catch (IOException localIOException) {
/* 108 */       throw new SmartUploadException("File can't be saved (1120).");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void fileToField(ResultSet paramResultSet, String paramString)
/*     */     throws ServletException, IOException, SmartUploadException, SQLException
/*     */   {
/* 123 */     long l = 0L;
/* 124 */     int i = 65536;
/* 125 */     int j = 0;
/*     */ 
/* 127 */     int k = this.m_startData;
/*     */ 
/* 130 */     if (paramResultSet == null) throw new IllegalArgumentException("The RecordSet cannot be null (1145).");
/*     */ 
/* 132 */     if (paramString == null) throw new IllegalArgumentException("The columnName cannot be null (1150).");
/*     */ 
/* 134 */     if (paramString.length() == 0) throw new IllegalArgumentException("The columnName cannot be empty (1155).");
/*     */ 
/* 138 */     l = BigInteger.valueOf(this.m_size).divide(BigInteger.valueOf(i)).longValue();
/*     */ 
/* 142 */     j = BigInteger.valueOf(this.m_size).mod(BigInteger.valueOf(i)).intValue();
/*     */     try
/*     */     {
/* 148 */       for (int i1 = 1; i1 < l; ++i1) {
/* 149 */         paramResultSet.updateBinaryStream(paramString, new ByteArrayInputStream(this.m_parent.m_binArray, k, i), i);
/*     */ 
/* 152 */         k = (k == 0) ? 1 : k;
/*     */ 
/* 154 */         k = i1 * i + this.m_startData;
/*     */       }
/*     */ 
/* 157 */       if (j > 0) {
/* 158 */         paramResultSet.updateBinaryStream(paramString, new ByteArrayInputStream(this.m_parent.m_binArray, k, j), j);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 165 */       byte[] arrayOfByte = new byte[this.m_size];
/* 166 */       System.arraycopy(this.m_parent.m_binArray, this.m_startData, arrayOfByte, 0, this.m_size);
/*     */ 
/* 168 */       paramResultSet.updateBytes(paramString, arrayOfByte);
/*     */     }
/*     */     catch (Exception localException) {
/* 171 */       throw new SmartUploadException("Unable to save file in the DataBase (1130).");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isMissing()
/*     */   {
/* 183 */     return this.m_isMissing;
/*     */   }
/*     */ 
/*     */   public String getFieldName()
/*     */   {
/* 193 */     return this.m_fieldname;
/*     */   }
/*     */ 
/*     */   public String getFileName()
/*     */   {
/* 203 */     return this.m_filename;
/*     */   }
/*     */ 
/*     */   public String getFilePathName()
/*     */   {
/* 213 */     return this.m_filePathName;
/*     */   }
/*     */ 
/*     */   public String getFileExt()
/*     */   {
/* 223 */     return this.m_fileExt;
/*     */   }
/*     */ 
/*     */   public String getContentType()
/*     */   {
/* 233 */     return this.m_contentType;
/*     */   }
/*     */ 
/*     */   public String getContentDisp()
/*     */   {
/* 243 */     return this.m_contentDisp;
/*     */   }
/*     */ 
/*     */   public String getContentString()
/*     */   {
/* 256 */     String str = new String(this.m_parent.m_binArray, this.m_startData, this.m_size);
/* 257 */     return str;
/*     */   }
/*     */ 
/*     */   public String getTypeMIME()
/*     */     throws IOException
/*     */   {
/* 268 */     return this.m_typeMime;
/*     */   }
/*     */ 
/*     */   public String getSubTypeMIME()
/*     */   {
/* 278 */     return this.m_subTypeMime;
/*     */   }
/*     */ 
/*     */   public int getSize()
/*     */   {
/* 288 */     return this.m_size;
/*     */   }
/*     */ 
/*     */   protected int getStartData()
/*     */   {
/* 298 */     return this.m_startData;
/*     */   }
/*     */ 
/*     */   protected int getEndData()
/*     */   {
/* 307 */     return this.m_endData;
/*     */   }
/*     */ 
/*     */   protected void setParent(SmartUpload paramSmartUpload)
/*     */   {
/* 314 */     this.m_parent = paramSmartUpload;
/*     */   }
/*     */ 
/*     */   protected void setStartData(int paramInt)
/*     */   {
/* 321 */     this.m_startData = paramInt;
/*     */   }
/*     */ 
/*     */   protected void setEndData(int paramInt)
/*     */   {
/* 328 */     this.m_endData = paramInt;
/*     */   }
/*     */ 
/*     */   protected void setSize(int paramInt)
/*     */   {
/* 336 */     this.m_size = paramInt;
/*     */   }
/*     */ 
/*     */   protected void setIsMissing(boolean paramBoolean)
/*     */   {
/* 347 */     this.m_isMissing = paramBoolean;
/*     */   }
/*     */ 
/*     */   protected void setFieldName(String paramString)
/*     */   {
/* 355 */     this.m_fieldname = paramString;
/*     */   }
/*     */ 
/*     */   protected void setFileName(String paramString)
/*     */   {
/* 363 */     this.m_filename = paramString;
/*     */   }
/*     */ 
/*     */   protected void setFilePathName(String paramString)
/*     */   {
/* 371 */     this.m_filePathName = paramString;
/*     */   }
/*     */ 
/*     */   protected void setFileExt(String paramString)
/*     */   {
/* 379 */     this.m_fileExt = paramString;
/*     */   }
/*     */ 
/*     */   protected void setContentType(String paramString)
/*     */   {
/* 387 */     this.m_contentType = paramString;
/*     */   }
/*     */ 
/*     */   protected void setContentDisp(String paramString)
/*     */   {
/* 395 */     this.m_contentDisp = paramString;
/*     */   }
/*     */ 
/*     */   protected void setTypeMIME(String paramString)
/*     */   {
/* 403 */     this.m_typeMime = paramString;
/*     */   }
/*     */ 
/*     */   protected void setSubTypeMIME(String paramString)
/*     */   {
/* 411 */     this.m_subTypeMime = paramString;
/*     */   }
/*     */ 
/*     */   public byte getBinaryData(int paramInt)
/*     */   {
/* 424 */     if (this.m_startData + paramInt > this.m_endData) {
/* 425 */       throw new ArrayIndexOutOfBoundsException("Index Out of range (1115).");
/*     */     }
/*     */ 
/* 428 */     if (this.m_startData + paramInt <= this.m_endData)
/* 429 */       return this.m_parent.m_binArray[(this.m_startData + paramInt)];
/* 430 */     return 0;
/*     */   }
/*     */ }

/* Location:           E:\smart-upload\
 * Qualified Name:     com.jspsmart.upload.File
 * JD-Core Version:    0.5.4
 */