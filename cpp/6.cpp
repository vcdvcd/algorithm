#include <iostream>
#include <cmath>
#include <iomanip>
using namespace std;
int main(int argc, char *argv[])
{
	int n,i,j,k,c,d,f,s,m,x;
	int a[100][100];
	int b;
	cin>>n;
	b= int(log2(n));
	for(i = 0;i < n;i++){
		a[0][i]=(i+1);
	}
	for(j=0;j<n;j++){
		if(j % 2 ==0){
			a[1][j+1] = a[0][j];
		}else{
			a[1][j-1] = a[0][j];
		}
	}
	
	for(k =1;k<b;k++){
		x = int(pow(2.0,double(k)));
		for(c= 0;c<=n-x;c+=x){
			for(d =c;d<c+x;d++){
				for(f=0;f<x;f++){
					if((c/x)%2==0){
						a[f+x][d+x]=a[f][d];
					}else{
						a[f+x][d-x]=a[f][d];
					}
				}
			}
		}
	}
	for(s=0;s<n;s++){
		for(m =0;m<n;m++){
			cout<<a[s][m]<<setw(5);
		}
		cout<<endl;
	}
	return 0;
}