db.createUser(	
   { 
       user : "root",
       pwd  : "oracle",
       roles: [{ role: "userAdmin", db  :  "oracle" }]
   }
)
