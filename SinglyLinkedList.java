
import java.util.*;

public class SinglyLinkedList<E extends Comparable<E>> {

    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;

    private static class Node<E> {

        private E element;
        private Node<E> next;

        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> n) {
            next = n;
        }
    }

    public SinglyLinkedList() {

    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {
        if (isEmpty()) {
            return null;
        }
        return head.getElement();
    }

    public E last() {
        if (isEmpty()) {
            return null;
        }
        return tail.getElement();
    }

    public void addFirst(E e) {
        head = new Node<>(e, head);

        if (isEmpty()) {
            tail = head;
        }
        size++;
    }

    public void addLast(E e) {
        Node<E> newest = new Node<>(e, null);
        if (isEmpty()) {
            head = newest;
        } else {
            tail.setNext(newest);
        }
        tail = newest;
        size++;
    }

    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }

        E answer = head.getElement();
        head = head.getNext();
        size--;

        if (isEmpty()) {
            tail = null;
        }
        return answer;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> current = head;
        while (current != null) {
            sb.append(current.getElement());
            sb.append(" ");
            current = current.getNext();
        }
        return sb.toString();
    }

    // write your codes here
    public void swap() {
        // store all the elements in an array
        List<E> elements = new ArrayList<>();
        // stores elements and its corresponding node
        Map<E, Node<E>> nodeMap = new HashMap<>();
        // stores nodes and their previous node
        Map<Node<E>, Node<E>> prevMap = new HashMap<>();
        // stores already swapped nodes
        Set<Node<E>> swappedNodes = new HashSet<>();

        Node<E> currentNode = head;
        Node<E> prevNode = null;
        while (currentNode != null) {
            E currentElement = currentNode.getElement();

            elements.add(currentElement);
            nodeMap.put(currentElement, currentNode);
            prevMap.put(currentNode, prevNode);

            prevNode = currentNode;
            currentNode = currentNode.getNext();
        }
        Collections.sort(elements); // sort the elements to find pairs

        currentNode = head;
        prevNode = null;
        while (currentNode != null) {
            if (swappedNodes.contains(currentNode)) {
                prevNode = currentNode;
                currentNode = currentNode.getNext();
                continue;
            }

            E currentElement = currentNode.getElement();
            int currentIndex = elements.indexOf(currentElement);

            int pairIndex = elements.size() - 1 - currentIndex;

            if (currentIndex == pairIndex) {
                prevNode = currentNode;
                currentNode = currentNode.getNext();
                continue;
            }

            E pairElement = elements.get(pairIndex);

            Node<E> pairNode = nodeMap.get(pairElement);
            Node<E> prevPairNode = prevMap.get(pairNode);
            Node<E> nextPairNode = pairNode.getNext();
            Node<E> nextNode = currentNode.getNext();

            // swapping
            // edge case
            if (nextNode == pairNode) {
                if (prevNode == null) {
                    head = pairNode;
                } else {
                    prevNode.setNext(pairNode);
                }
                pairNode.setNext(currentNode);      // pair points to current
                currentNode.setNext(nextPairNode);  // current points to what pair had

                // Update tail
                if (nextPairNode == null) {
                    tail = currentNode;
                }
            } else {
                if (prevNode == null) {
                    head = pairNode;
                } else {
                    prevNode.setNext(pairNode);
                }
                pairNode.setNext(nextNode);
                currentNode.setNext(nextPairNode);
                prevPairNode.setNext(currentNode);
                if (nextPairNode == null) {
                    tail = currentNode;
                }
                if (nextNode == null) {
                    tail = pairNode;
                }

            }

            // updating
            prevMap.put(pairNode, prevNode);
            prevMap.put(currentNode, prevPairNode);
            if (nextNode != null) {
                prevMap.put(nextNode, pairNode);
            }
            if (nextPairNode != null) {
                prevMap.put(nextPairNode, currentNode);
            }
            swappedNodes.add(currentNode);
            swappedNodes.add(pairNode);
            currentNode = pairNode.getNext();
            prevNode = pairNode;
        }

        //System.out.println(elements.toString());
    }

}
