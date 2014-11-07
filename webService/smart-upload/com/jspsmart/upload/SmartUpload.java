package com.jspsmart.upload;

/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ 
/*     */ public class SmartUpload
/*     */ {
/*     */   protected byte[] m_binArray;
/*     */   protected HttpServletRequest m_request;
/*     */   protected HttpServletResponse m_response;
/*     */   protected ServletContext m_application;
/*     */   private int m_totalBytes;
/*     */   private int m_currentIndex;
/*     */   private int m_startData;
/*     */   private int m_endData;
/*     */   private String m_boundary;
/*     */   private long m_totalMaxFileSize;
/*     */   private long m_maxFileSize;
/*     */   private Vector m_deniedFilesList;
/*     */   private Vector m_allowedFilesList;
/*     */   private boolean m_denyPhysicalPath;
/*     */   private boolean m_forcePhysicalPath;
/*     */   private String m_contentDisposition;
/*     */   public static final int SAVE_AUTO = 0;
/*     */   public static final int SAVE_VIRTUAL = 1;
/*     */   public static final int SAVE_PHYSICAL = 2;
/*     */   private Files m_files;
/*     */   private Request m_formRequest;
/*     */ 
/*     */   public SmartUpload()
/*     */   {
/*  47 */     this.m_totalBytes = 0;
/*  48 */     this.m_currentIndex = 0;
/*  49 */     this.m_startData = 0;
/*  50 */     this.m_endData = 0;
/*  51 */     this.m_boundary = new String();
/*  52 */     this.m_totalMaxFileSize = 0L;
/*  53 */     this.m_maxFileSize = 0L;
/*  54 */     this.m_deniedFilesList = new Vector();
/*  55 */     this.m_allowedFilesList = new Vector();
/*  56 */     this.m_denyPhysicalPath = false;
/*  57 */     this.m_forcePhysicalPath = false;
/*  58 */     this.m_contentDisposition = new String();
/*  59 */     this.m_files = new Files();
/*  60 */     this.m_formRequest = new Request();
/*     */   }
/*     */ 
/*     */   public final void init(ServletConfig servletconfig)
/*     */     throws ServletException
/*     */   {
/*  66 */     this.m_application = servletconfig.getServletContext();
/*     */   }
/*     */ 
/*     */   public void service(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  72 */     this.m_request = httpservletrequest;
/*  73 */     this.m_response = httpservletresponse;
/*     */   }
/*     */ 
/*     */   public final void initialize(ServletConfig servletconfig, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
/*     */     throws ServletException
/*     */   {
/*  79 */     this.m_application = servletconfig.getServletContext();
/*  80 */     this.m_request = httpservletrequest;
/*  81 */     this.m_response = httpservletresponse;
/*     */   }
/*     */ 
/*     */   public final void initialize(PageContext pagecontext)
/*     */     throws ServletException
/*     */   {
/*  87 */     this.m_application = pagecontext.getServletContext();
/*  88 */     this.m_request = ((HttpServletRequest)pagecontext.getRequest());
/*  89 */     this.m_response = ((HttpServletResponse)pagecontext.getResponse());
/*     */   }
/*     */ 
/*     */   public final void initialize(ServletContext servletcontext, HttpSession httpsession, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, JspWriter jspwriter)
/*     */     throws ServletException
/*     */   {
/*  95 */     this.m_application = servletcontext;
/*  96 */     this.m_request = httpservletrequest;
/*  97 */     this.m_response = httpservletresponse;
/*     */   }
/*     */ 
/*     */   public void upload()
/*     */     throws ServletException, IOException, SmartUploadException
/*     */   {
/* 103 */     int i = 0;
/* 104 */     boolean flag = false;
/* 105 */     long l = 0L;
/* 106 */     boolean flag1 = false;
/* 107 */     String s = new String();
/* 108 */     String s2 = new String();
/* 109 */     String s4 = new String();
/* 110 */     String s5 = new String();
/* 111 */     String s6 = new String();
/* 112 */     String s7 = new String();
/* 113 */     String s8 = new String();
/* 114 */     String s9 = new String();
/* 115 */     String s10 = new String();
/* 116 */     boolean flag2 = false;
/* 117 */     this.m_totalBytes = this.m_request.getContentLength();
/* 118 */     this.m_binArray = new byte[this.m_totalBytes];
/*     */     int j=0;
/* 120 */     for (; i < this.m_totalBytes; i += j) {
/*     */       
/*     */       try {
/* 123 */         this.m_request.getInputStream();
/* 124 */         j = this.m_request.getInputStream().read(this.m_binArray, i, this.m_totalBytes - i);
/*     */       }
/*     */       catch (Exception exception)
/*     */       {
/* 128 */         throw new SmartUploadException("Unable to upload.");
/*     */       }
/*     */     }
/* 131 */     for (; (!flag1) && (this.m_currentIndex < this.m_totalBytes); this.m_currentIndex += 1) {
/* 132 */       if (this.m_binArray[this.m_currentIndex] == 13)
/* 133 */         flag1 = true;
/*     */       else
/* 135 */         this.m_boundary += (char)this.m_binArray[this.m_currentIndex];
/*     */     }
/* 137 */     if (this.m_currentIndex == 1)
/* 138 */       return;
/* 139 */     for (this.m_currentIndex += 1; this.m_currentIndex < this.m_totalBytes; this.m_currentIndex += 2)
/*     */     {
/* 141 */       String s1 = getDataHeader();
/* 142 */       this.m_currentIndex += 2;
/* 143 */       boolean flag3 = s1.indexOf("filename") > 0;
/* 144 */       String s3 = getDataFieldValue(s1, "name");
/* 145 */       if (flag3)
/*     */       {
/* 147 */         s6 = getDataFieldValue(s1, "filename");
/* 148 */         s4 = getFileName(s6);
/* 149 */         s5 = getFileExt(s4);
/* 150 */         s7 = getContentType(s1);
/* 151 */         s8 = getContentDisp(s1);
/* 152 */         s9 = getTypeMIME(s7);
/* 153 */         s10 = getSubTypeMIME(s7);
/*     */       }
/* 155 */       getDataSection();
/* 156 */       if ((flag3) && (s4.length() > 0))
/*     */       {
/* 158 */         if (this.m_deniedFilesList.contains(s5))
/* 159 */           throw new SecurityException("The extension of the file is denied to be uploaded (1015).");
/* 160 */         if ((!this.m_allowedFilesList.isEmpty()) && (!this.m_allowedFilesList.contains(s5)))
/* 161 */           throw new SecurityException("The extension of the file is not allowed to be uploaded (1010).");
/* 162 */         if ((this.m_maxFileSize > 0L) && (this.m_endData - this.m_startData + 1 > this.m_maxFileSize))
/* 163 */           throw new SecurityException("Size exceeded for this file : " + s4 + " (1105).");
/* 164 */         l += this.m_endData - this.m_startData + 1;
/* 165 */         if ((this.m_totalMaxFileSize > 0L) && (l > this.m_totalMaxFileSize))
/* 166 */           throw new SecurityException("Total File Size exceeded (1110).");
/*     */       }
/* 168 */       if (flag3)
/*     */       {
/* 170 */         File file = new File();
/* 171 */         file.setParent(this);
/* 172 */         file.setFieldName(s3);
/* 173 */         file.setFileName(s4);
/* 174 */         file.setFileExt(s5);
/* 175 */         file.setFilePathName(s6);
/* 176 */         file.setIsMissing(s6.length() == 0);
/* 177 */         file.setContentType(s7);
/* 178 */         file.setContentDisp(s8);
/* 179 */         file.setTypeMIME(s9);
/* 180 */         file.setSubTypeMIME(s10);
/* 181 */         if (s7.indexOf("application/x-macbinary") > 0)
/* 182 */           this.m_startData += 128;
/* 183 */         file.setSize(this.m_endData - this.m_startData + 1);
/* 184 */         file.setStartData(this.m_startData);
/* 185 */         file.setEndData(this.m_endData);
/* 186 */         this.m_files.addFile(file);
/*     */       }
/*     */       else {
/* 189 */         String s11 = new String(this.m_binArray, this.m_startData, this.m_endData - this.m_startData + 1, "UTF-8");
/* 190 */         this.m_formRequest.putParameter(s3, s11);
/*     */       }
/* 192 */       if ((char)this.m_binArray[(this.m_currentIndex + 1)] == '-')
/*     */         return;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int save(String s)
/*     */     throws ServletException, IOException, SmartUploadException
/*     */   {
/* 201 */     return save(s, 0);
/*     */   }
/*     */ 
/*     */   public int save(String s, int i)
/*     */     throws ServletException, IOException, SmartUploadException
/*     */   {
/* 207 */     int j = 0;
/* 208 */     if (s == null)
/* 209 */       s = this.m_application.getRealPath("/");
/* 210 */     if (s.indexOf("/") != -1)
/*     */     {
/* 212 */       if (s.charAt(s.length() - 1) != '/')
/* 213 */         s = s + "/";
/*     */     }
/* 215 */     else if (s.charAt(s.length() - 1) != '\\')
/* 216 */       s = s + "\\";
/* 217 */     for (int k = 0; k < this.m_files.getCount(); ++k) {
/* 218 */       if (this.m_files.getFile(k).isMissing())
/*     */         continue;
/* 220 */       this.m_files.getFile(k).saveAs(s + this.m_files.getFile(k).getFileName(), i);
/* 221 */       ++j;
/*     */     }
/*     */ 
/* 224 */     return j;
/*     */   }
/*     */ 
/*     */   public int getSize()
/*     */   {
/* 229 */     return this.m_totalBytes;
/*     */   }
/*     */ 
/*     */   public byte getBinaryData(int i)
/*     */   {
/*     */     byte byte0;
/*     */     try
/*     */     {
/* 237 */       byte0 = this.m_binArray[i];
/*     */     }
/*     */     catch (Exception exception)
/*     */     {
/* 241 */       throw new ArrayIndexOutOfBoundsException("Index out of range (1005).");
/*     */     }/*     */    
/* 243 */     return byte0;
/*     */   }
/*     */ 
/*     */   public Files getFiles()
/*     */   {
/* 248 */     return this.m_files;
/*     */   }
/*     */ 
/*     */   public Request getRequest()
/*     */   {
/* 253 */     return this.m_formRequest;
/*     */   }
/*     */ 
/*     */   public void downloadFile(String s)
/*     */     throws ServletException, IOException, SmartUploadException
/*     */   {
/* 259 */     downloadFile(s, null, null);
/*     */   }
/*     */ 
/*     */   public void downloadFile(String s, String s1)
/*     */     throws ServletException, IOException, SmartUploadException, SmartUploadException
/*     */   {
/* 265 */     downloadFile(s, s1, null);
/*     */   }
/*     */ 
/*     */   public void downloadFile(String s, String s1, String s2)
/*     */     throws ServletException, IOException, SmartUploadException
/*     */   {
/* 271 */     downloadFile(s, s1, s2, 65000);
/*     */   }
/*     */ 
/*     */   public void downloadFile(String s, String s1, String s2, int i)
/*     */     throws ServletException, IOException, SmartUploadException
/*     */   {
/* 277 */     if (s == null)
/* 278 */       throw new IllegalArgumentException("File '" + s + "' not found (1040).");
/* 279 */     if (s.equals(""))
/* 280 */       throw new IllegalArgumentException("File '" + s + "' not found (1040).");
/* 281 */     if ((!isVirtual(s)) && (this.m_denyPhysicalPath))
/* 282 */       throw new SecurityException("Physical path is denied (1035).");
/* 283 */     if (isVirtual(s))
/* 284 */       s = this.m_application.getRealPath(s);
/* 285 */     java.io.File file = new java.io.File(s);
/* 286 */     FileInputStream fileinputstream = new FileInputStream(file);
/* 287 */     long l = file.length();
/* 288 */     boolean flag = false;
/* 289 */     int k = 0;
/* 290 */     byte[] abyte0 = new byte[i];
/* 291 */     if (s1 == null) {
/* 292 */       this.m_response.setContentType("application/x-msdownload");
/*     */     }
/* 294 */     else if (s1.length() == 0)
/* 295 */       this.m_response.setContentType("application/x-msdownload");
/*     */     else
/* 297 */       this.m_response.setContentType(s1);
/* 298 */     this.m_response.setContentLength((int)l);
/* 299 */     this.m_contentDisposition = ((this.m_contentDisposition != null) ? this.m_contentDisposition : "attachment;");
/* 300 */     if (s2 == null) {
/* 301 */       this.m_response.setHeader("Content-Disposition", this.m_contentDisposition + " filename=" + getFileName(s));
/*     */     }
/* 303 */     else if (s2.length() == 0)
/* 304 */       this.m_response.setHeader("Content-Disposition", this.m_contentDisposition);
/*     */     else
/* 306 */       this.m_response.setHeader("Content-Disposition", this.m_contentDisposition + " filename=" + s2);
/* 307 */     while (k < l)
/*     */     {
/* 309 */       int j = fileinputstream.read(abyte0, 0, i);
/* 310 */       k += j;
/* 311 */       this.m_response.getOutputStream().write(abyte0, 0, j);
/*     */     }
/* 313 */     fileinputstream.close();
/*     */   }
/*     */ 
/*     */   public void downloadField(ResultSet resultset, String s, String s1, String s2)
/*     */     throws ServletException, IOException, SQLException
/*     */   {
/* 319 */     if (resultset == null)
/* 320 */       throw new IllegalArgumentException("The RecordSet cannot be null (1045).");
/* 321 */     if (s == null)
/* 322 */       throw new IllegalArgumentException("The columnName cannot be null (1050).");
/* 323 */     if (s.length() == 0)
/* 324 */       throw new IllegalArgumentException("The columnName cannot be empty (1055).");
/* 325 */     byte[] abyte0 = resultset.getBytes(s);
/* 326 */     if (s1 == null) {
/* 327 */       this.m_response.setContentType("application/x-msdownload");
/*     */     }
/* 329 */     else if (s1.length() == 0)
/* 330 */       this.m_response.setContentType("application/x-msdownload");
/*     */     else
/* 332 */       this.m_response.setContentType(s1);
/* 333 */     this.m_response.setContentLength(abyte0.length);
/* 334 */     if (s2 == null) {
/* 335 */       this.m_response.setHeader("Content-Disposition", "attachment;");
/*     */     }
/* 337 */     else if (s2.length() == 0)
/* 338 */       this.m_response.setHeader("Content-Disposition", "attachment;");
/*     */     else
/* 340 */       this.m_response.setHeader("Content-Disposition", "attachment; filename=" + s2);
/* 341 */     this.m_response.getOutputStream().write(abyte0, 0, abyte0.length);
/*     */   }
/*     */ 
/*     */   public void fieldToFile(ResultSet resultset, String s, String s1)
/*     */     throws ServletException, IOException, SmartUploadException, SQLException
/*     */   {
/*     */     try
/*     */     {
/* 349 */       if (this.m_application.getRealPath(s1) != null)
/* 350 */         s1 = this.m_application.getRealPath(s1);
/* 351 */       InputStream inputstream = resultset.getBinaryStream(s);
/* 352 */       FileOutputStream fileoutputstream = new FileOutputStream(s1);
/*     */       int i;
/* 354 */       while ((i = inputstream.read()) != -1)
/*     */       {/*     */         
/* 355 */         fileoutputstream.write(i);
/* 356 */       }fileoutputstream.close();
/*     */     }
/*     */     catch (Exception exception)
/*     */     {
/* 360 */       throw new SmartUploadException("Unable to save file from the DataBase (1020).");
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getDataFieldValue(String s, String s1)
/*     */   {
/* 366 */     String s2 = new String();
/* 367 */     String s3 = new String();
/* 368 */     int i = 0;
/* 369 */     boolean flag = false;
/* 370 */     boolean flag1 = false;
/* 371 */     boolean flag2 = false;
/* 372 */     s2 = s1 + "=" + '"';
/* 373 */     i = s.indexOf(s2);
/* 374 */     if (i > 0)
/*     */     {
/* 376 */       int j = i + s2.length();
/* 377 */       int k = j;
/* 378 */       s2 = "\"";
/* 379 */       int l = s.indexOf(s2, j);
/* 380 */       if ((k > 0) && (l > 0))
/* 381 */         s3 = s.substring(k, l);
/*     */     }
/* 383 */     return s3;
/*     */   }
/*     */ 
/*     */   private String getFileExt(String s)
/*     */   {
/* 388 */     String s1 = new String();
/* 389 */     int i = 0;
/* 390 */     int j = 0;
/* 391 */     if (s == null)
/* 392 */       return null;
/* 393 */     i = s.lastIndexOf('.') + 1;
/* 394 */     j = s.length();
/* 395 */     s1 = s.substring(i, j);
/* 396 */     if (s.lastIndexOf('.') > 0) {
/* 397 */       return s1;
/*     */     }
/* 399 */     return "";
/*     */   }
/*     */ 
/*     */   private String getContentType(String s)
/*     */   {
/* 404 */     String s1 = new String();
/* 405 */     String s2 = new String();
/* 406 */     int i = 0;
/* 407 */     boolean flag = false;
/* 408 */     s1 = "Content-Type:";
/* 409 */     i = s.indexOf(s1) + s1.length();
/* 410 */     if (i != -1)
/*     */     {
/* 412 */       int j = s.length();
/* 413 */       s2 = s.substring(i, j);
/*     */     }
/* 415 */     return s2;
/*     */   }
/*     */ 
/*     */   private String getTypeMIME(String s)
/*     */   {
/* 420 */     String s1 = new String();
/* 421 */     int i = 0;
/* 422 */     i = s.indexOf("/");
/* 423 */     if (i != -1) {
/* 424 */       return s.substring(1, i);
/*     */     }
/* 426 */     return s;
/*     */   }
/*     */ 
/*     */   private String getSubTypeMIME(String s)
/*     */   {
/* 431 */     String s1 = new String();
/* 432 */     int i = 0;
/* 433 */     boolean flag = false;
/* 434 */     i = s.indexOf("/") + 1;
/* 435 */     if (i != -1)
/*     */     {
/* 437 */       int j = s.length();
/* 438 */       return s.substring(i, j);
/*     */     }
/*     */ 
/* 441 */     return s;
/*     */   }
/*     */ 
/*     */   private String getContentDisp(String s)
/*     */   {
/* 447 */     String s1 = new String();
/* 448 */     int i = 0;
/* 449 */     int j = 0;
/* 450 */     i = s.indexOf(":") + 1;
/* 451 */     j = s.indexOf(";");
/* 452 */     s1 = s.substring(i, j);
/* 453 */     return s1;
/*     */   }
/*     */ 
/*     */   private void getDataSection()
/*     */   {
/* 458 */     boolean flag = false;
/* 459 */     String s = new String();
/* 460 */     int i = this.m_currentIndex;
/* 461 */     int j = 0;
/* 462 */     int k = this.m_boundary.length();
/* 463 */     this.m_startData = this.m_currentIndex;
/* 464 */     this.m_endData = 0;
/* 465 */     while (i < this.m_totalBytes)
/* 466 */       if (this.m_binArray[i] == (byte)this.m_boundary.charAt(j))
/*     */       {
/* 468 */         if (j == k - 1)
/*     */         {
/* 470 */           this.m_endData = (i - k + 1 - 3);
/* 471 */           break;
/*     */         }
/* 473 */         ++i;
/* 474 */         ++j;
/*     */       }
/*     */       else {
/* 477 */         ++i;
/* 478 */         j = 0;
/*     */       }
/* 480 */     this.m_currentIndex = (this.m_endData + k + 3);
/*     */   }
/*     */ 
/*     */   private String getDataHeader()
/*     */   {
/* 485 */     int i = this.m_currentIndex;
/* 486 */     int j = 0;
/* 487 */     boolean flag = false;
/* 488 */     for (boolean flag1 = false; !flag1; ) {
/* 489 */       if ((this.m_binArray[this.m_currentIndex] == 13) && (this.m_binArray[(this.m_currentIndex + 2)] == 13))
/*     */       {
/* 491 */         flag1 = true;
/* 492 */         j = this.m_currentIndex - 1;
/* 493 */         this.m_currentIndex += 2;
/*     */       }
/*     */       else {
/* 496 */         this.m_currentIndex += 1;
/*     */       }
/*     */     }
/* 499 */     String s = new String(this.m_binArray, i, j - i + 1);
/* 500 */     return s;
/*     */   }
/*     */ 
/*     */   private String getFileName(String s)
/*     */   {
/* 505 */     String s1 = new String();
/* 506 */     String s2 = new String();
/* 507 */     int i = 0;
/* 508 */     boolean flag = false;
/* 509 */     boolean flag1 = false;
/* 510 */     boolean flag2 = false;
/* 511 */     i = s.lastIndexOf('/');
/* 512 */     if (i != -1)
/* 513 */       return s.substring(i + 1, s.length());
/* 514 */     i = s.lastIndexOf('\\');
/* 515 */     if (i != -1) {
/* 516 */       return s.substring(i + 1, s.length());
/*     */     }
/* 518 */     return s;
/*     */   }
/*     */ 
/*     */   public void setDeniedFilesList(String s)
/*     */     throws ServletException, IOException, SQLException
/*     */   {
/* 524 */     String s1 = "";
/* 525 */     if (s != null)
/*     */     {
/* 527 */       String s2 = "";
/* 528 */       for (int i = 0; i < s.length(); ++i) {
/* 529 */         if (s.charAt(i) == ',')
/*     */         {
/* 531 */           if (!this.m_deniedFilesList.contains(s2))
/* 532 */             this.m_deniedFilesList.addElement(s2);
/* 533 */           s2 = "";
/*     */         }
/*     */         else {
/* 536 */           s2 = s2 + s.charAt(i);
/*     */         }
/*     */       }
/* 539 */       if (s2 != "")
/* 540 */         this.m_deniedFilesList.addElement(s2);
/*     */     }
/*     */     else {
/* 543 */       this.m_deniedFilesList = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAllowedFilesList(String s)
/*     */   {
/* 549 */     String s1 = "";
/* 550 */     if (s != null)
/*     */     {
/* 552 */       String s2 = "";
/* 553 */       for (int i = 0; i < s.length(); ++i) {
/* 554 */         if (s.charAt(i) == ',')
/*     */         {
/* 556 */           if (!this.m_allowedFilesList.contains(s2))
/* 557 */             this.m_allowedFilesList.addElement(s2);
/* 558 */           s2 = "";
/*     */         }
/*     */         else {
/* 561 */           s2 = s2 + s.charAt(i);
/*     */         }
/*     */       }
/* 564 */       if (s2 != "")
/* 565 */         this.m_allowedFilesList.addElement(s2);
/*     */     }
/*     */     else {
/* 568 */       this.m_allowedFilesList = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setDenyPhysicalPath(boolean flag)
/*     */   {
/* 574 */     this.m_denyPhysicalPath = flag;
/*     */   }
/*     */ 
/*     */   public void setForcePhysicalPath(boolean flag)
/*     */   {
/* 579 */     this.m_forcePhysicalPath = flag;
/*     */   }
/*     */ 
/*     */   public void setContentDisposition(String s)
/*     */   {
/* 584 */     this.m_contentDisposition = s;
/*     */   }
/*     */ 
/*     */   public void setTotalMaxFileSize(long l)
/*     */   {
/* 589 */     this.m_totalMaxFileSize = l;
/*     */   }
/*     */ 
/*     */   public void setMaxFileSize(long l)
/*     */   {
/* 594 */     this.m_maxFileSize = l;
/*     */   }
/*     */ 
/*     */   protected String getPhysicalPath(String s, int i)
/*     */     throws IOException
/*     */   {
/* 600 */     String s1 = new String();
/* 601 */     String s2 = new String();
/* 602 */     String s3 = new String();
/* 603 */     boolean flag = false;
/* 604 */     s3 = System.getProperty("file.separator");
/* 605 */     if (s == null)
/* 606 */       throw new IllegalArgumentException("There is no specified destination file (1140).");
/* 607 */     if (s.equals(""))
/* 608 */       throw new IllegalArgumentException("There is no specified destination file (1140).");
/* 609 */     if (s.lastIndexOf("\\") >= 0)
/*     */     {
/* 611 */       s1 = s.substring(0, s.lastIndexOf("\\"));
/* 612 */       s2 = s.substring(s.lastIndexOf("\\") + 1);
/*     */     }
/* 614 */     if (s.lastIndexOf("/") >= 0)
/*     */     {
/* 616 */       s1 = s.substring(0, s.lastIndexOf("/"));
/* 617 */       s2 = s.substring(s.lastIndexOf("/") + 1);
/*     */     }
/* 619 */     s1 = (s1.length() != 0) ? s1 : "/";
/* 620 */     java.io.File file = new java.io.File(s1);
/* 621 */     if (file.exists())
/* 622 */       flag = true;
/* 623 */     if (i == 0)
/*     */     {
/* 625 */       if (isVirtual(s1))
/*     */       {
/* 627 */         s1 = this.m_application.getRealPath(s1);
/* 628 */         if (s1.endsWith(s3))
/* 629 */           s1 = s1 + s2;
/*     */         else
/* 631 */           s1 = s1 + s3 + s2;
/* 632 */         return s1;
/*     */       }
/* 634 */       if (flag)
/*     */       {
/* 636 */         if (this.m_denyPhysicalPath) {
/* 637 */           throw new IllegalArgumentException("Physical path is denied (1125).");
/*     */         }
/* 639 */         return s;
/*     */       }
/*     */ 
/* 642 */       throw new IllegalArgumentException("This path does not exist (1135).");
/*     */     }
/*     */ 
/* 645 */     if (i == 1)
/*     */     {
/* 647 */       if (isVirtual(s1))
/*     */       {
/* 649 */         s1 = this.m_application.getRealPath(s1);
/* 650 */         if (s1.endsWith(s3))
/* 651 */           s1 = s1 + s2;
/*     */         else
/* 653 */           s1 = s1 + s3 + s2;
/* 654 */         return s1;
/*     */       }
/* 656 */       if (flag) {
/* 657 */         throw new IllegalArgumentException("The path is not a virtual path.");
/*     */       }
/* 659 */       throw new IllegalArgumentException("This path does not exist (1135).");
/*     */     }
/* 661 */     if (i == 2)
/*     */     {
/* 663 */       if (flag) {
/* 664 */         if (this.m_denyPhysicalPath) {
/* 665 */           throw new IllegalArgumentException("Physical path is denied (1125).");
/*     */         }
/* 667 */         return s;
/* 668 */       }if (isVirtual(s1)) {
/* 669 */         throw new IllegalArgumentException("The path is not a physical path.");
/*     */       }
/* 671 */       throw new IllegalArgumentException("This path does not exist (1135).");
/*     */     }
/*     */ 
/* 674 */     return null;
/*     */   }
/*     */ 
/*     */   public void uploadInFile(String s)
/*     */     throws IOException, SmartUploadException
/*     */   {
/* 681 */     int i = 0;
/* 682 */     int j = 0;
/* 683 */     boolean flag = false;
/* 684 */     if (s == null)
/* 685 */       throw new IllegalArgumentException("There is no specified destination file (1025).");
/* 686 */     if (s.length() == 0)
/* 687 */       throw new IllegalArgumentException("There is no specified destination file (1025).");
/* 688 */     if ((!isVirtual(s)) && (this.m_denyPhysicalPath))
/* 689 */       throw new SecurityException("Physical path is denied (1035).");
/* 690 */     i = this.m_request.getContentLength();
/* 691 */     this.m_binArray = new byte[i];
/*     */     int k;
/* 693 */     for (; j < i; j += k) {/*     */       
/*     */       try {
/* 696 */         k = this.m_request.getInputStream().read(this.m_binArray, j, i - j);
/*     */       }
/*     */       catch (Exception exception)
/*     */       {
/* 700 */         throw new SmartUploadException("Unable to upload.");
/*     */       }
/*     */     }
/* 703 */     if (isVirtual(s))
/* 704 */       s = this.m_application.getRealPath(s);
/*     */     try
/*     */     {
/* 707 */       java.io.File file = new java.io.File(s);
/* 708 */       FileOutputStream fileoutputstream = new FileOutputStream(file);
/* 709 */       fileoutputstream.write(this.m_binArray);
/* 710 */       fileoutputstream.close();
/*     */     }
/*     */     catch (Exception exception1)
/*     */     {
/* 714 */       throw new SmartUploadException("The Form cannot be saved in the specified file (1030).");
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isVirtual(String s)
/*     */   {
/* 720 */     if (this.m_application.getRealPath(s) != null)
/*     */     {
/* 722 */       java.io.File file = new java.io.File(this.m_application.getRealPath(s));
/* 723 */       return file.exists();
/*     */     }
/*     */ 
/* 726 */     return false;
/*     */   }
/*     */ }

/* Location:           E:\smart-upload\
 * Qualified Name:     com.jspsmart.upload.SmartUpload
 * JD-Core Version:    0.5.4
 */