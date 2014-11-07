package com.jspsmart.upload;

/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class Files
/*     */ {
/*     */   private SmartUpload m_parent;
/*  25 */   private Hashtable m_files = new Hashtable();
/*  26 */   private int m_counter = 0;
/*     */ 
/*     */   protected void addFile(File paramFile)
/*     */   {
/*  41 */     if (paramFile == null) {
/*  42 */       throw new IllegalArgumentException("newFile cannot be null.");
/*     */     }
/*     */ 
/*  45 */     this.m_files.put(new Integer(this.m_counter), paramFile);
/*  46 */     this.m_counter += 1;
/*     */   }
/*     */ 
/*     */   public File getFile(int paramInt)
/*     */   {
/*  65 */     if (paramInt < 0) {
/*  66 */       throw new IllegalArgumentException("File's index cannot be a negative value (1210).");
/*     */     }
/*     */ 
/*  69 */     File localFile = (File)this.m_files.get(new Integer(paramInt));
/*     */ 
/*  72 */     if (localFile == null) {
/*  73 */       throw new IllegalArgumentException("Files' name is invalid or does not exist (1205).");
/*     */     }
/*     */ 
/*  76 */     return localFile;
/*     */   }
/*     */ 
/*     */   public int getCount()
/*     */   {
/*  89 */     return this.m_counter;
/*     */   }
/*     */ 
/*     */   public long getSize()
/*     */     throws IOException
/*     */   {
/* 102 */     long l = 0L;
/* 103 */     for (int i = 0; i < this.m_counter; ++i) {
/* 104 */       l += getFile(i).getSize();
/*     */     }
/* 106 */     return l;
/*     */   }
/*     */ 
/*     */   public Collection getCollection()
/*     */   {
/* 118 */     return this.m_files.values();
/*     */   }
/*     */ 
/*     */   public Enumeration getEnumeration()
/*     */   {
/* 130 */     return this.m_files.elements();
/*     */   }
/*     */ }

/* Location:           E:\smart-upload\
 * Qualified Name:     com.jspsmart.upload.Files
 * JD-Core Version:    0.5.4
 */