#include <iostream>
#include <cmath>
using namespace std;
int main(int argc, char *argv[])
{
	
	int n;
	int m;
	double a[10];
	int i=1;
	long int ans; 
	cin>>n>>m;
	a[0]=1;
	while(i<=n){
		a[i]=((n-1)*(a[i-1])-m)/n;
		if(a[i]!=floor(a[i])){
			i=1;
			a[0]++;
		}
		else{
			i++;
		}	
	}
	ans=a[0]*n+m;
	cout<<ans;
	return 0;
}
//134217721