cd "C:/Users/Admin/Downloads"
gen bdate1=date(birthdate, "MDY")
format bdate1 %td
gen bdate2=date(birthdate, "MD19Y")
format bdate2 %td
gen bdate3=date(birthdate, "YY")
format bdate3 %ty
gen byear=yofd(bdate1)
format byear %ty

