#include <iostream>
#include <vector>
#include <cmath>
using namespace std;
int dp[1001];
int f1(int t,vector<vector<int> > v){
	int n = v.size();
	for(int j =0;j<n;j++){
		for(int i = t;i >= 0;i--){
			if(i - v[j][0] >= 0)
				dp[i] = max(dp[i],dp[i - v[j][0]] + v[j][1]);
		}
	}
	return dp[t];
}
int main(int argc, char *argv[])
{
	int t;
	int c;
	vector<vector<int> > a;
	int n;
	int cnt;
	int b = 0;
	vector<int> v;
	cin>>t>>c;
	while(b < c){
		cnt = 0;
		v.clear();
		while(cin>>n){
			v.push_back(n);
			cnt++;
			if(cnt == 2) break;
		}
		a.push_back(v);
		b++;
	}
	cout<<f1(t,a);
	return 0;
}