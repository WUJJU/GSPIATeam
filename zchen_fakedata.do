replace birthdate = "9/9/1940" in 1
generate countryofbirth = "NA"
replace countryofbirth = "US" if strpos(placeofbirth, "United States")
replace countryofbirth = "SP" if strpos(placeofbirth, "Singapore")
replace countryofbirth = "UR" if strpos(placeofbirth, "Ukraine")
replace countryofbirth = "UR" if strpos(placeofbirth, "Kiev")
replace countryofbirth = "US" if strpos(placeofbirth, "Pittsburgh, PA")
egen artistid = group(fullname)
*This creates a categorical variable of artists. Alternatively:
*gen artistid=_n
*sort fullname
*by fullname: gen artistitid=1 if _n==1
*replace artistid=sum(artistid)
*replace artistid=. if missing(fullname)
egen mediumid = group(mediumdescription)
egen departmentid = group(department)
quietly forvalues i=1(1)4{
	graph pie if artistid==`i', over(mediumid) 
	graph export C:\Users\Ziqiao\Documents\CMOA\graph\mediumartist`i', as(png)
}
 
