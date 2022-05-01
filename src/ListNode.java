import java.util.HashMap;
import java.util.Stack;

public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    public static ListNode reverseList(ListNode head) {
        ListNode p = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = p;
            p = cur;
            cur = next;
        }
        return p;
    }

    public static ListNode reverseList1(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode newHead = reverseList1(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    //408算法原题：设A的长度为a+c，B的长度为b+c；其中c为A、B的公共部分；
    //拼接AB、BA：A+B=a+c+b+c B+A=b+c+a+c；由于a+c+b=b+c+a，因此二者必定在c的起始点处相遇
    //这题的链表都是无环的（考虑有环的见getIntersectionNode2）
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null)
            return null;
        ListNode node1 = headA;
        ListNode node2 = headB;
        while (node1 != node2) {
            node1 = node1 == null ? headB : node1.next;
            node2 = node2 == null ? headA : node2.next;
        }
        return node1;
    }

    //检测是否是回文链表
    //方法1 使用栈结构（速度和空间复杂度都不太行）
    public static boolean isPalindrome(ListNode head) {
        Stack<Integer> stack = new Stack<>();
        ListNode cur = head;
        while (cur != null) {
            stack.push(cur.val);
            cur = cur.next;
        }
        while (head != null) {
            if (head.val != stack.pop())
                return false;
            head = head.next;
        }
        return true;
    }

    //方法2：找到中点，将中点后面的元素放入栈中
    public static boolean isPalindrome2(ListNode head) {
        if (head == null || head.next == null)
            return true;
        ListNode cur = head;//快指针
        ListNode right = head.next;//慢指针，在快指针的右侧这样可以使得最后慢指针指向中点右侧一位
        Stack<Integer> stack = new Stack<>();
        //快慢指针
        while (cur.next != null && cur.next.next != null) {
            right = right.next;
            cur = cur.next.next;
        }
        while (right != null) {
            stack.push(right.val);
            right = right.next;
        }
        while (!stack.isEmpty()) {
            if (stack.pop() != head.val)
                return false;
            head = head.next;
        }
        return true;
    }

    public static boolean isPalindrome3(ListNode head) {
        if (head == null || head.next == null)
            return true;
        ListNode cur = head;
        ListNode mid = head;
        ListNode p = null;
        while (cur.next != null && cur.next.next != null) {
            cur = cur.next.next;
            mid = mid.next;
        }
        //反转中点位置以后的链表
        while (mid != null) {
            ListNode next = mid.next;
            mid.next = p;
            p = mid;
            mid = next;
        }
        ListNode p1 = p;
        cur = head;
        while (p != null && cur != null) {
            if (p.val != cur.val)
                return false;
            p = p.next;
            cur = cur.next;
        }
        //还原链表
        p = null;
        while (p1 != null) {
            ListNode next = p1.next;
            p1.next = p;
            p = p1;
            p1 = next;
        }
        return true;
    }

    public static ListNode show(ListNode head) {
        ListNode h = head;
        while (h != null) {
            System.out.println(h.val);
            h = h.next;
        }
        return h;
    }

    public static ListNode partition(ListNode head, int x) {
        ListNode sH = null;
        ListNode sT = null;
        ListNode eH = null;
        ListNode eT = null;
        ListNode mH = null;
        ListNode mT = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = null;
            if (head.val < x) {
                if (sH == null) {
                    sH = head;
                    sT = head;
                } else {
                    sT.next = head;
                    sT = head;
                }
            } else if (head.val == x) {
                if (eH == null) {
                    eH = head;
                    eT = head;
                } else {
                    eT.next = head;
                    eT = head;
                }
            } else {
                if (mH == null) {
                    mH = head;
                    mT = head;
                } else {
                    mT.next = head;
                    mT = head;
                }
            }
            head = next;
        }
        if (sT != null) {
            sT.next = eH;
            eT = eT != null ? eT : sT;
        }
        if (eT != null) {
            eT.next = mH;
        }
        return sH != null ? sH : (eH != null ? eH : mH);
    }
//可能有环可能无环的链表的第一个重合节点
    public static ListNode getLoop(ListNode head) {
        if (head == null || head.next == null || head.next.next == null)
            return null;
        ListNode cur = head;
        ListNode n1 = cur.next;
        ListNode n2 = cur.next.next;
        while (n1 != n2) {
            if (n2.next != null && n2.next.next != null) {
                n1 = n1.next;
                n2 = n2.next.next;
            }
            return null;
        }
        n2 = head;
        while (n1 != n2) {
            n1 = n1.next;
            n2 = n2.next;
        }
        return n1;
    }

    public static ListNode noLoop(ListNode head1, ListNode head2) {
        ListNode cur1 = head1;
        ListNode cur2 = head2;
        int n = 0;
        while (cur1 != null) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2 != null) {
            n--;
            cur2 = cur2.next;
        }
        cur1 = n > 0 ? head1 : head2;
        cur2 = cur1 == head1 ? head2 : head1;
        n = Math.abs(n);
        while (n != 0) {
            n--;
            cur1 = cur1.next;
        }
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    public static ListNode bothLoop(ListNode head1, ListNode loop1, ListNode loop2, ListNode head2) {
        int n = 0;
        ListNode cur1 = null;
        ListNode cur2 = null;
        if (loop1 == loop2) {
            cur1 = head1;
            cur2 = head2;
            while (cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }
            while (cur2 != loop2) {
                n--;
                cur2 = cur2.next;
            }
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n != 0) {
                n--;
                cur1 = cur1.next;
            }
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        } else {
            cur1 = loop1.next;
            while (cur1 != loop1) {
                if (cur1 == loop2)
                    return cur1;
                cur1 = cur1.next;
            }
            return null;
        }
    }

    public static ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        ListNode loop1 = getLoop(headA);
        ListNode loop2 = getLoop(headB);
        if (loop1 == null && loop2 == null) {
            return noLoop(headA, headB);
        }
        if (loop1 != null & loop2 != null)
            return bothLoop(headA, loop1, headB, loop2);
        return null;
    }

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        ListNode listNode2 = new ListNode(4);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(2);
        ListNode listNode5 = new ListNode(5);
        ListNode listNode6 = new ListNode(2);
        listNode.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = listNode6;
//        ListNode listNode1 = reverseList1(listNode);
        System.out.println(partition(listNode, 3));
        show(listNode);
    }
}
