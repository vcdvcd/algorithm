#include <iostream>
#include <vector>
#include <cmath>
using namespace std;
int a[1000];
int ans = 0;
bool check(int i,int j){
	for(int m = 0;m <i;m++){
		if(a[m] == j || abs(m - i) == abs(a[m] -j))
			return false;	
	}
	return true;
}
void f(int i,int n){
	if(i == n){
		ans++;
		return;
	}
	for(int j =0;j<n;j++){
		if(check(i,j)){
			a[i] = j;
			f(i+1,n);
		}
	}
}
int main(int argc, char *argv[])
{
	int n;
	cin>>n;
	f(0,n);
	cout<<ans<<endl;
	return 0;
}