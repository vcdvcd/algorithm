#include <iostream>
#include <cmath>
#include <iomanip>
using namespace std;
int main(int argc, char *argv[])
{
	int n;
	int a;
	int t=0;
	int b[100][2];
	double dis;
	double ans=10000000;
	cin>>n;
	while(cin>>a){
		if(t < n*2){
			b[t/2][t%2]=a;
			t++;
			if(t == n*2)
		 		break;
		}
	}
	for(int m =0;m<n-1;m++){
		for(int c=m+1;c<n;c++){
			dis=sqrt((double)((b[m][0]-b[c][0])*(b[m][0]-b[c][0])+(b[m][1]-b[c][1])*(b[m][1]-b[c][1])));
			if(ans > dis){
				ans=dis;
			}
		}
	}
	cout<<fixed<<setprecision(2)<<ans;
	return 0;
}