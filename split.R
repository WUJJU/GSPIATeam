attach (FakeHackathonData) 
Full.name
x<- Full.name
X<-matrix (x)
names<- strsplit(X, " ")
names
haha  <- matrix(unlist(names), ncol=2, byrow=TRUE)
df   <- as.data.frame(haha)
colnames(df) <- c("Last", "First")
df

attach (FakeHackathonData) 
y<- Dimensions..HxWxD.in.inches.
Y<-matrix (y)
Y
dimension<- strsplit(Y, "x")
dimension
hehe  <- matrix(unlist(dimension), ncol=3, byrow=TRUE)
yiha   <- as.data.frame(hehe)
colnames(yiha) <- c("Height", "Width", "Depth")
yiha

