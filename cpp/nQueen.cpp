#include <iostream>
#include <cmath>
using namespace std;
int a[20];
int res;
bool check(int i,int j){
	for(int k =0;k<i;k++){
		if(a[k] == j || abs(a[k]-j)==abs(k-i))
			return false;
	}
	return true;
}
void nQueen(int n,int i){
	if(i==n){
		res++;
		return;
	}
	for(int j = 0;j <n;j++){
		if(check(i,j)){
			a[i] = j;
			nQueen(n,i+1);
		}
	}
}
int main(int argc, char *argv[])
{
	int n;
	cin>>n;
	nQueen(n,0);
	cout<<res;
	return 0;
}