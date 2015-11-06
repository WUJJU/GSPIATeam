setwd("C:/Users/Admin/Downloads")
install.packages('lubridate')
library('lubridate')
fake <- read.csv("C:/Users/Admin/Downloads/fake.csv")
fix('fake')
bdate1 = as.Date(fake$Birth.date, format = '%m/%d/%Y')
bdate3 = as.Date(fake$Birth.date, format = '%d/%b/%Y')

bdate2 = mdy(fake$Birth.date)
bdate4 = dmy(fake$Birth.date)

bdate1
bdate2
bdate3
bdate4

as.Date('1/15/2001',format='%m/%d/%Y')
as.Date('April 26, 2001',format='%B %d, %Y')
as.Date('22JUN01',format='%d%b%y')
as.Date(strptime(xx, format = " %d-%m-%Y %H:%M", tz = "UTC"))
format(strptime(xx, format = " %d-%m-%Y %H:%M", tz = "UTC"), format = "%d-%m-%Y")

str1 <- " 29-12-2014 06:37 UTC"
sub('[ ]+([^ ]+) .*', '\\1', str1)

library(lubridate)
format(dmy_hm(str1),'%d-%m-%Y')

str2 <- c(str1, '29.12.14 06/37 UTC')
format(dmy_hm(str2), '%d-%m-%Y')


# clean dates: use specified date format; if not specified, try the formats
# specified in the "else" sub-clause:
if (!is.null(clean_options$date_formats)) {
  date_formats      <- clean_options$date_formats
}else{
  date_formats      <- c("%d%B%Y","%d%B%y")
}

# clean some covariates:
if ("ENRLDATE" %in% X_extra) {
  # if ENRLDATE is in the dataset, transform the date format
  #Rdate.case <- as.Date(datcase_X_extra_subset$ENRLDATE, "%d%B%Y")
  #Rdate.ctrl <- as.Date(datctrl_X_extra_subset$ENRLDATE, "%d%B%Y")
  Rdate.case <-
    lubridate::parse_date_time(datacase$X$ENRLDATE, date_formats)
  Rdate.ctrl <-
    lubridate::parse_date_time(datactrl$X$ENRLDATE, date_formats)
  
  uniq.month.case <-
    unique(paste(
      lubridate::month(Rdate.case),lubridate::year(Rdate.case),sep = "-"
    ))
  uniq.month.ctrl <-
    unique(paste(
      lubridate::month(Rdate.ctrl),lubridate::year(Rdate.ctrl),sep = "-"
    ))
  
  
  
  #symm.diff.dates <- as.set(uniq.month.case)%D%as.set(uniq.month.ctrl)
  #if (length(symm.diff.dates)!=0){
  #  cat("Cases and controls have different enrollment months:","\n")
  #  print(symm.diff.dates)
  #}
  datacase$X$ENRLDATE <- Rdate.case
  datactrl$X$ENRLDATE <- Rdate.ctrl
}
if ("patid" %in% X_extra) {
  datacase$X$patid <- as.character(datacase$X$patid)
  datactrl$X$patid <- as.character(datactrl$X$patid)
}
if ("newSITE" %in% X_extra) {
  datacase$X$newSITE <- as.character(datacase$X$newSITE)
  datactrl$X$newSITE <- as.character(datactrl$X$newSITE)
}


?strptime
?parse_date_time
