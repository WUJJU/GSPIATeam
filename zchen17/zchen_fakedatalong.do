drop v2
generate countryofbirth = ""
replace countryofbirth = "US" if strpos(placeofbirth, "United States")
replace countryofbirth = "UK" if strpos(placeofbirth, "UK")
replace countryofbirth = "China" if strpos(placeofbirth, "China")
replace countryofbirth = "India" if strpos(placeofbirth, "India")
replace countryofbirth = "SP" if strpos(placeofbirth, "Singapore")
replace countryofbirth = "UR" if strpos(placeofbirth, "Ukraine")
replace countryofbirth = "UR" if strpos(placeofbirth, "Kiev")
replace countryofbirth = "US" if strpos(placeofbirth, "Pittsburgh, PA")

egen artistid=group(fullname)
egen mediumid = group(mediumdescription)
egen departmentid = group(department)
*This creates a unique numerical identifier for each artist who has a unique name.
*equivalent to:
*gen artistid=_n
*sort fullname
*by fullname: gen artistitid=1 if _n==1
*replace artistid=sum(artistid)
*replace artistid=. if missing(fullname)
forvalues i=1(1)10{
	graph pie if artistid==`i', over(mediumid) 
	graph export C:\Users\Ziqiao\Documents\CMOA\graph\mediumbyartist`i', as(png)
}
*This type of loops exports a graph (created by the second line of codes) for each value of the grouping variable.
* In the first line, a(b)c is the series {a,a+b,a+2b,...,c}
forvalues i=1(1)12{
	graph pie if horoscope==`i', over(mediumid)
	graph export C:\Users\Ziqiao\Documents\CMOA\graph\mediumbyhoroscope`i', as(png)
}
forvalues i=1(1)10{
	twoway (scatter mediumid departmentid) if artistid==`i', ytitle(medium) xtitle(department) title(artist`i')
	graph export C:\Users\Ziqiao\Documents\CMOA\graph\scatter_mediumbyartist`i', as(png)
}
