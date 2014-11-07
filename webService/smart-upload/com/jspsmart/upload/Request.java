package com.jspsmart.upload;

/*    */ 
/*    */ import java.util.Enumeration;
/*    */ import java.util.Hashtable;
/*    */ 
/*    */ public class Request
/*    */ {
/*    */   private Hashtable m_parameters;
/*    */   private int m_counter;
/*    */ 
/*    */   Request()
/*    */   {
/* 20 */     this.m_parameters = new Hashtable();
/* 21 */     this.m_counter = 0;
/*    */   }
/*    */ 
/*    */   protected void putParameter(String s, String s1)
/*    */   {
/* 26 */     if (s == null)
/* 27 */       throw new IllegalArgumentException("The name of an element cannot be null.");
/* 28 */     if (this.m_parameters.containsKey(s))
/*    */     {
/* 30 */       Hashtable hashtable = (Hashtable)this.m_parameters.get(s);
/* 31 */       hashtable.put(new Integer(hashtable.size()), s1);
/*    */     }
/*    */     else {
/* 34 */       Hashtable hashtable1 = new Hashtable();
/* 35 */       hashtable1.put(new Integer(0), s1);
/* 36 */       this.m_parameters.put(s, hashtable1);
/* 37 */       this.m_counter += 1;
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getParameter(String s)
/*    */   {
/* 43 */     if (s == null)
/* 44 */       throw new IllegalArgumentException("Form's name is invalid or does not exist (1305).");
/* 45 */     Hashtable hashtable = (Hashtable)this.m_parameters.get(s);
/* 46 */     if (hashtable == null) {
/* 47 */       return null;
/*    */     }
/*    */ 
/* 50 */     return (String)hashtable.get(new Integer(0));
/*    */   }
/*    */ 
/*    */   public Enumeration getParameterNames()
/*    */   {
/* 56 */     return this.m_parameters.keys();
/*    */   }
/*    */ 
/*    */   public String[] getParameterValues(String s)
/*    */   {
/* 61 */     if (s == null)
/* 62 */       throw new IllegalArgumentException("Form's name is invalid or does not exist (1305).");
/* 63 */     Hashtable hashtable = (Hashtable)this.m_parameters.get(s);
/* 64 */     if (hashtable == null)
/* 65 */       return null;
/* 66 */     String[] as = new String[hashtable.size()];
/* 67 */     for (int i = 0; i < hashtable.size(); ++i) {
/* 68 */       as[i] = ((String)hashtable.get(new Integer(i)));
/*    */     }
/* 70 */     return as;
/*    */   }
/*    */ }

/* Location:           E:\smart-upload\
 * Qualified Name:     com.jspsmart.upload.Request
 * JD-Core Version:    0.5.4
 */