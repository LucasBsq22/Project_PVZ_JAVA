import React from "react";

interface DeleteModeToggleProps {
  deleteMode: boolean;
  setDeleteMode: (mode: boolean) => void;
}

const DeleteModeToggle: React.FC<DeleteModeToggleProps> = ({ deleteMode, setDeleteMode }) => {
  return (
    <button
      onClick={() => setDeleteMode(!deleteMode)}
      style={{
        backgroundColor: deleteMode ? "red" : "gray",
        color: "white",
        padding: "10px 20px",
        marginBottom: "10px",
        cursor: "pointer",
        border: "none",
        borderRadius: "5px",
        fontWeight: "bold",
      }}
    >
      {deleteMode ? "Mode Suppression : ACTIVÉ" : "Mode Suppression : DÉSACTIVÉ"}
    </button>
  );
};

export default DeleteModeToggle;
