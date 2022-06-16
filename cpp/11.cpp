#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;
int ans =0;
int a[1000];
vector<int> x(1000,-1);
void f(int sum,int cnt,int i,int r,int n){
	if(i == n) return;
	if(r < sum - cnt) return;
	x[i] = 1;
	if(cnt + a[i] == sum){
		ans++;
		for(int j = 0;j<=i;j++){
			if(x[j] == 1){
				cout<<a[j]<<"  ";
			}
		}
		cout<<endl;
	}else if(cnt + a[i] + a[i + 1] <= sum){
			f(sum,cnt+a[i],i+1,r - a[i],n);
	}
	if(cnt + r - a[i] >= sum && cnt + a[i + 1]<=sum){ 
		x[i] = 0;
		f(sum,cnt,i+1,r - a[i],n);
	}
}
int main(int argc, char *argv[])
{
	int n;
	int sum;
	int i =0;
	int rest = 0; 
	cin>>n>>sum;
	for(int j = 0;j<n;j++){
		cin>>i;
		a[j] = i;
		rest += i;
	}
	if(rest < sum){
		cout<<0;
		return 0;
	}
	sort(a,a+n);
	f(sum,0,0,rest,n);
	cout<<ans;
	return 0;
}