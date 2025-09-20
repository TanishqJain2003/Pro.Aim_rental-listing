import React, { useEffect, useState } from "react";

interface Payment {
  id: number;
  amount: number;
  status: string;
  paymentDate: string;
  dueDate: string;
  paymentDescription?: string;
  method?: string;
}

const PaymentsPage: React.FC = () => {
  const [payments, setPayments] = useState<Payment[]>([]);
  const [loading, setLoading] = useState(true);

  // Form state
  const [amount, setAmount] = useState("");
  const [description, setDescription] = useState("");
  const [method, setMethod] = useState("CASH");
  const [dueDate, setDueDate] = useState("");

  // Fetch payments from backend
  const fetchPayments = () => {
    fetch("http://localhost:8080/api/payments")
      .then((res) => res.json())
      .then((data) => {
        setPayments(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching payments:", err);
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchPayments();
  }, []);

  // ✅ Handle create payment (fixed)
  const handleCreatePayment = (e: React.FormEvent) => {
    e.preventDefault();

    const newPayment = {
      paymentReference: "PAY-" + Date.now(), // auto-generated
      amount: parseFloat(amount),
      paymentDescription: description,
      method: method,
      status: "PENDING",
      paymentDate: new Date().toISOString(),
      dueDate: dueDate
        ? new Date(dueDate).toISOString()
        : new Date().toISOString(),

      // 🔹 temporary associations (replace with dropdown later)
      tenant: { id: 2 },
      landlord: { id: 1 },
      property: { id: 5 }
    };

    fetch("http://localhost:8080/api/payments", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newPayment),
    })
      .then((res) => res.json())
      .then(() => {
        fetchPayments();
        setAmount("");
        setDescription("");
        setMethod("CASH");
        setDueDate("");
      })
      .catch((err) => console.error("Error creating payment:", err));
  };

  if (loading) return <p>Loading payments...</p>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Manage Payments</h1>
      <p className="text-gray-600 mb-6">
        Here you can track and manage rent payments.
      </p>

      {/* Create Payment Form */}
      <form
        onSubmit={handleCreatePayment}
        className="mb-6 p-4 border rounded-lg shadow-sm bg-gray-50"
      >
        <h2 className="text-lg font-semibold mb-3">Add New Payment</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <input
            type="number"
            placeholder="Amount"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            required
            className="p-2 border rounded"
          />
          <input
            type="date"
            value={dueDate}
            onChange={(e) => setDueDate(e.target.value)}
            required
            className="p-2 border rounded"
          />
          <input
            type="text"
            placeholder="Description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            className="p-2 border rounded"
          />
          <select
            value={method}
            onChange={(e) => setMethod(e.target.value)}
            className="p-2 border rounded"
          >
            <option value="CASH">Cash</option>
            <option value="BANK_TRANSFER">Bank Transfer</option>
            <option value="CREDIT_CARD">Credit Card</option>
            <option value="DEBIT_CARD">Debit Card</option>
          </select>
        </div>
        <button
          type="submit"
          className="mt-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
        >
          Add Payment
        </button>
      </form>

      {/* Payments Table */}
      {payments.length === 0 ? (
        <p className="text-gray-500">No payments found.</p>
      ) : (
        <table className="min-w-full border border-gray-300 shadow-md rounded-lg">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 border">ID</th>
              <th className="px-4 py-2 border">Amount</th>
              <th className="px-4 py-2 border">Status</th>
              <th className="px-4 py-2 border">Payment Date</th>
              <th className="px-4 py-2 border">Due Date</th>
              <th className="px-4 py-2 border">Description</th>
              <th className="px-4 py-2 border">Method</th>
            </tr>
          </thead>
          <tbody>
            {payments.map((payment) => (
              <tr key={payment.id} className="hover:bg-gray-50">
                <td className="px-4 py-2 border">{payment.id}</td>
                <td className="px-4 py-2 border">₹{payment.amount}</td>
                <td className="px-4 py-2 border">{payment.status}</td>
                <td className="px-4 py-2 border">
                  {new Date(payment.paymentDate).toLocaleDateString()}
                </td>
                <td className="px-4 py-2 border">
                  {new Date(payment.dueDate).toLocaleDateString()}
                </td>
                <td className="px-4 py-2 border">
                  {payment.paymentDescription || "-"}
                </td>
                <td className="px-4 py-2 border">{payment.method || "-"}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default PaymentsPage;
