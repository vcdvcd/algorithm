#include <iostream>
#include <cmath>
using namespace std;
int dp[1001];
int main(int argc, char *argv[])
{
	int t,m;
	int a[1001][2];
	cin>>t>>m;
	for(int i =0;i < m;i++){
		for(int j = 0;j < 2;j++){
			cin>>a[i][j];
		}
	}
	for(int i = 0;i < m;i++){
		for(int j = t;j >= a[i][0];j--)
			dp[j] = max(dp[j],dp[j - a[i][0]] + a[i][1]);
	}
	cout<<dp[t];
	return 0;
}