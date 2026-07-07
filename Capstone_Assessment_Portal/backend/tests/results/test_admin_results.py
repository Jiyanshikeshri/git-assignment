from fastapi import status


def test_admin_dashboard_success(
    client,
    admin_token,
):
    """
    Verify that the admin can retrieve the complete quiz result dashboard
    """

    response = client.get(
        "/results/",
        headers={
            "Authorization": f"Bearer {admin_token}",
        },
    )

    assert response.status_code == status.HTTP_200_OK

    data = response.json()

    assert isinstance(data, list)
    assert len(data) > 0

    assert "id" in data[0]
    assert "attempt_id" in data[0]
    assert "student_id" in data[0]
    assert "quiz_id" in data[0]
    assert "score" in data[0]
    assert "percentage" in data[0]
    assert "result_status" in data[0]


def test_student_cannot_access_admin_dashboard(
    client,
    student_token,
):
    """
    Verify that a student cannot access the admin result dashboard
    """

    response = client.get(
        "/results/",
        headers={
            "Authorization": f"Bearer {student_token}",
        },
    )

    assert response.status_code == status.HTTP_403_FORBIDDEN