<h2>状压DP（理解困难😂）</h2>

1.（状压DP不是本题较好的解）我能赢吗https://leetcode-cn.com/problems/can-i-win/

2.(状压DP不是本题较好的解)火柴拼正方形https://leetcode-cn.com/problems/matchsticks-to-square/

3.优美的排序https://leetcode-cn.com/problems/beautiful-arrangement/

<h2>区间DP（缺乏熟练度）</h2>

1.(个人认为难题)猜数字大小IIhttps://leetcode-cn.com/problems/guess-number-higher-or-lower-ii/

<h2>
    01背包问题 与 完全背包问题
</h2>
<h5>0.转换为背包问题</h5>

1.目标和https://leetcode-cn.com/problems/target-sum/

<h5>1.多重背包问题</h5>





<h5>2.多维背包问题</h5>

1.一和零https://leetcode-cn.com/problems/ones-and-zeroes/

2.大礼包(6维背包,只能作为一种思路实际上不可能纯dp写)https://leetcode-cn.com/problems/shopping-offers/


<h2>
    股票问题
</h2>
1.买卖股票的最佳时机含手续费https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/

2.最佳买卖股票时机含冷冻期https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/

<h2> 
    其他DP
</h2>
1.环绕字符串中唯一的子字符串https://leetcode-cn.com/problems/unique-substrings-in-wraparound-string/

关键思想：**最长长度就是以字母为结尾可以组成不同字符串的个数**

2.只有两个键的键盘https://leetcode-cn.com/problems/2-keys-keyboard/

关键思想：**本题就是因式分解**

3.最大加号标志https://leetcode-cn.com/problems/largest-plus-sign/

关键思想：**就是穷举四个方向的连续1，求出其中最小值，最后遍历dp数组求最大值**

4.多米诺和托米诺平铺https://leetcode-cn.com/problems/domino-and-tromino-tiling/

关键思想：**考虑基本模型，然后缩小问题范围，最后推出递推公式**



<h2>
    回文问题
</h2>

<h5>1.回文子序列</h5>

1.最长回文子序列https://leetcode-cn.com/problems/longest-palindromic-subsequence/

<h5>2.回文子串</h5>

1.最长回文子串https://leetcode-cn.com/problems/longest-palindromic-substring/

2.回文子串https://leetcode-cn.com/problems/palindromic-substrings/

<h2>
    多维度DP
</h2>
1.出界的路径数https://leetcode-cn.com/problems/out-of-boundary-paths/

2.骑士在棋盘上的概率https://leetcode-cn.com/problems/knight-probability-in-chessboard/

<h2>
    最长公共子序列问题
</h2>

1.两个字符串的删除操作https://leetcode-cn.com/problems/delete-operation-for-two-strings/

<h1>上下两个问题的核心代码区别</h1>

<code>if(元素1 == 元素2){</code>

<code>	dp[i] [j] = dp[i - 1] [j - 1] + 1; </code>

<code>}</code>

<code>else{</code>

<code>	dp[i] [j] = Math.max(dp[i - 1] [j],dp[i] [j - 1]);</code>

<code>}</code>

**上述代码是对于最长子序列的解决，如果是最长子串或者子数组的话，那么else部分的代码不需要,来个变量来记录dp[i] [j]的最大值即可**



<h2>
    最长公共子数组或者子串问题
</h2>

1.最长重复子数组https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray/

<h2>
    序列DP
</h2>

1.最长递增子序列https://leetcode-cn.com/problems/longest-increasing-subsequence/

2.最长递增子序列个数https://leetcode-cn.com/problems/number-of-longest-increasing-subsequence/(1的变种)

3.两个字符串的最小ASCII删除和https://leetcode-cn.com/problems/minimum-ascii-delete-sum-for-two-strings/

<h2>打家劫舍及相关问题</h2>

1.(打家劫舍问题变种)删除并获得点数https://leetcode-cn.com/problems/delete-and-earn/

<h2>船新DP(涉及图论)</h2>

<h3>1.Bellman Ford</h3>

1.K站中转内最便宜的航班https://leetcode-cn.com/problems/cheapest-flights-within-k-stops/